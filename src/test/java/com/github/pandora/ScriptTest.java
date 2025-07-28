package com.github.pandora;

import com.github.pandora.antlr.AntlrCompiler;
import com.github.pandora.ast.GenerateContext;
import com.github.pandora.ast.ScriptNode;
import com.github.pandora.compile.CompileContext;
import com.github.pandora.compile.Compiler;
import com.github.pandora.data.Assert;
import com.github.pandora.data.Model;
import com.github.pandora.eval.EvalTemplate;
import com.github.pandora.eval.EvaluationContext;
import com.github.pandora.eval.EvaluationValue;
import com.github.pandora.exception.AssertException;
import com.github.pandora.exception.ParseException;
import com.github.pandora.utils.ClassLoaderUtils;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;

public class ScriptTest {

    private static final String SCRIPT_PATH = "script";

    private static final String SCRIPT_SUFFIX = ".script";

    private static final String ASSERT = "assert";

    private static final String NESTED = "nested_if";

    private static final String ASSERT_EXP = "assert_exception";

    private static final String ASSERT_EXP_MSG = "assert_exception_msg";

    private static final String BOX = "box";

    private static final String OVERFLOW = "arithmetic_overflow";

    private static final String STR = "string";

    private static final String INVALID_TYPE1 = "unsupported_type1";

    private static final String INVALID_TYPE2 = "unsupported_type2";

    private String getScriptPath(String name) {
        return SCRIPT_PATH + File.separatorChar + name + SCRIPT_SUFFIX;
    }

    public EvalTemplate getEvalTemplate(Class<?> klass, String scriptPath) throws Exception {
        InputStream stream = ClassLoaderUtils.getClassLoader().getResourceAsStream(scriptPath);
        Compiler compiler = new AntlrCompiler();
        ScriptNode node = compiler.compile(stream, klass == null ? new CompileContext() : new CompileContext(klass));
        GenerateContext ast = klass == null ? new GenerateContext() : new GenerateContext(klass);
        // GenerateContext.enableDebug();
        // GenerateContext.enableOutPutClassFile(ClassLoaderUtils.getClassLoader().getResource("").getPath());
        node.generateCode(ast);

        return ast.getTemplateClass().newInstance();
    }

    public EvalTemplate getEvalTemplate(String scriptPath) throws Exception {
        return getEvalTemplate(null, scriptPath);
    }

    @Test
    public void assertTest() throws Exception {
        EvalTemplate template = getEvalTemplate(Assert.class, getScriptPath(ASSERT));
        Assert model = new Assert();
        EvaluationContext context = new EvaluationContext();
        context.add("params", model);
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());
    }

    @Test
    public void nestedIfTest() throws Exception {
        EvalTemplate template = getEvalTemplate(Model.class, getScriptPath(NESTED));
        Model model = new Model();
        EvaluationContext context = new EvaluationContext();
        context.add("params", model);
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());
    }

    @Test(expected = AssertException.class)
    public void assertExceptionTest() throws Exception {
        EvalTemplate template = getEvalTemplate(getScriptPath(ASSERT_EXP));
        EvaluationContext context = new EvaluationContext();
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());
    }

    @Test(expected = AssertException.class)
    public void assertExceptionMsgTest() throws Exception {
        EvalTemplate template = getEvalTemplate(getScriptPath(ASSERT_EXP_MSG));
        EvaluationContext context = new EvaluationContext();
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());
    }

    @Test
    public void boxAndUnboxTest() throws Exception {
        EvalTemplate template = getEvalTemplate(Model.class, getScriptPath(BOX));
        Model model = new Model();
        EvaluationContext context = new EvaluationContext();
        context.add("params", model);
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());
    }

    @Test
    public void stringTest() throws Exception {
        EvalTemplate template = getEvalTemplate(Model.class, getScriptPath(STR));
        Model model = new Model();
        EvaluationContext context = new EvaluationContext();
        context.add("params", model);
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());
    }

    @Test(expected = ParseException.class)
    public void invalidType1Test() throws Exception {
        EvalTemplate template = getEvalTemplate(Model.class, getScriptPath(INVALID_TYPE1));
        Model model = new Model();
        EvaluationContext context = new EvaluationContext();
        context.add("params", model);
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());
    }

    @Test(expected = ParseException.class)
    public void invalidType2Test() throws Exception {
        EvalTemplate template = getEvalTemplate(Model.class, getScriptPath(INVALID_TYPE2));
        Model model = new Model();
        EvaluationContext context = new EvaluationContext();
        context.add("params", model);
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());
    }

    @Test
    public void overflowTest() throws Exception {
        EvalTemplate template = getEvalTemplate(Model.class, getScriptPath(OVERFLOW));
        Model model = new Model();
        EvaluationContext context = new EvaluationContext();
        context.add("params", model);
        EvaluationValue value = template.evaluate(context);
        System.out.println(value.getValue());

        System.out.println(of(1));//no of exception
    }

    public int of(int k) {
        return 2147483647 + k;
    }
}
