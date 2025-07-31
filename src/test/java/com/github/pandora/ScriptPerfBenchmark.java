package com.github.pandora;

import com.github.pandora.antlr.AntlrCompiler;
import com.github.pandora.compile.Compiler;
import com.github.pandora.ast.GenerateContext;
import com.github.pandora.ast.ScriptNode;
import com.github.pandora.compile.CompileContext;
import com.github.pandora.data.Model;
import com.github.pandora.eval.EvalTemplate;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import static org.openjdk.jmh.annotations.CompilerControl.Mode.DONT_INLINE;

@Warmup(iterations = 1, time = 5)
@Measurement(iterations = 1, time = 5)
@Fork(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class ScriptPerfBenchmark {

    private EvalTemplate template;

    @Setup
    public void init() throws Exception {
        initPandora(Thread.currentThread().getContextClassLoader().getResourceAsStream("script/benchmark.script"));
    }

    public void initPandora(InputStream stream) throws Exception {
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile(stream, new CompileContext(Model.class));

        GenerateContext ast = new GenerateContext(Model.class);
        // GenerateContext.enableDebug();
        // GenerateContext.enableOutPutClassFile(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        node.generateCode(ast);

        template = ast.getTemplateClass().newInstance();
    }

    @Benchmark
    public void testBaseline() {

    }

    @Benchmark
    @CompilerControl(DONT_INLINE)
    public Object testNative() {
        Model model = new Model();
        return invoke(model);
    }

    public Object invoke(Object o) {
        Model model = (Model) o;
        model.int1 = 666;
        model.int2 = 879;
        model.float1 = 12.34f;
        model.float2 = 13.44f;
        model.name1 = "my peter";
        model.flag1 = model.int1 >= model.int1;
        model.flag2 = model.float1 > model.int2;
        model.double1 = (model.float1 + model.float2) * model.int1;
        model.float3 = (model.float1 + model.float2) * model.int1;
        model.int1 = model.int1 * (model.int1 + model.int2);

        if(model.int1 < 100 || model.flag1)
            model.int3 = 100;
        else if (model.int1 > 800)
            model.int3 = 10;
        else if (model.int1 > 600)
            model.int3 = 1000;
        else
            model.int3 = 1;

        model.int2 = 0;
        while(model.int2 < 10)
            model.int2 = model.int2 + 1;

        return o;
    }

    @Benchmark
    @CompilerControl(DONT_INLINE)
    public Object testPandora() {
        Model model = new Model();
        return template.eval(model);
    }

    public static void main(final String[] args) throws Exception {
        Options opt = new OptionsBuilder().include(ScriptPerfBenchmark.class.getSimpleName())
                //.addProfiler(HotspotMemoryProfiler.class)
                //.addProfiler(GCProfiler.class)
                .build();
        new Runner(opt).run();
    }
}
