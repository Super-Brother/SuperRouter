package com.wenchao.annotation_compiler;

import com.google.auto.service.AutoService;
import com.wenchao.annotations.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * @author wenchao
 * @date 2019/7/28.
 * @time 8:36
 * description：注解处理器
 */
@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {

    /**
     * 生成文件的对象
     */
    Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    /**
     * 声明我们这个注解处理器要处理的注解时哪些
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 声明我们注解处理器支持的java sdk的版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 这个方法是注解处理器的核心方法，写文件就放在这里面进行
     *
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        final Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindPath.class);
        //初始化数据
        Map<String, String> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            TypeElement typeElement = (TypeElement) element;
            //获取到map中的key
            final String key = typeElement.getAnnotation(BindPath.class).value();
            final String value = typeElement.getQualifiedName().toString();
            map.put(key, value);
        }
        //开始写文件
        if (map.size() > 0) {
            Writer writer = null;
            //创建类名
            String utilName = "ActivityUtil" + System.currentTimeMillis();
            try {
                final JavaFileObject sourceFile = filer.createSourceFile("com.wenchao.util." + utilName);
                writer = sourceFile.openWriter();
                writer.write("package com.wenchao.util;\n" +
                        "\n" +
                        "import com.wenchao.router.ARouter;\n" +
                        "import com.wenchao.router.IRouter;\n" +
                        "\n" +
                        "public class " + utilName + " implements IRouter {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n");
                final Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    final String key = iterator.next();
                    final String value = map.get(key);
                    writer.write("ARouter.getInstance().putActivity(\"" + key + "\"," + value + ".class);\n");
                }
                writer.write("}\n}");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
}
