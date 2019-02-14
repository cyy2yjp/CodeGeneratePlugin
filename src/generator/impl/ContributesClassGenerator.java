package generator.impl;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.*;
import generator.ICodeGenerator;
import org.jetbrains.annotations.NotNull;

public class ContributesClassGenerator extends AbstractCodeGenerator implements ICodeGenerator {

    private final String METHOD_PRE = "provide";
    private   PsiClass contributeClass;

    public ContributesClassGenerator(PsiClass psiClass) {
        super(psiClass);

    }

    @Override
    protected void startGenerate() {
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(mClass.getProject());
        String bindClassName = mClass.getName();
        //生成绑定的类
        contributeClass = mClass.findInnerClassByName("CityStoreBuilder", false);

        System.out.println("contributeClass" + contributeClass);

        WriteCommandAction.runWriteCommandAction(contributeClass.getProject(), () -> {
            //如果没有创建
            if (contributeClass == null) {
                contributeClass = generateContributeClass(bindClassName, elementFactory);
                mClass.add(contributeClass);
            }
        });


        final PsiClass tempClass = contributeClass;
        WriteCommandAction.runWriteCommandAction(contributeClass.getProject(), () -> {
            findAndRemoveMethod(tempClass,generateContributeClass(bindClassName));
            PsiMethod contributesMethod = elementFactory.createMethodFromText(generateContributeMethod(bindClassName), tempClass);
            tempClass.add(contributesMethod);
        });

        //TODO remove existing element
        System.out.println("contributeClass" + contributeClass.getAllMethods().length);
        //让类实现接口
    }

    private PsiClass generateContributeClass(String bindClassName, PsiElementFactory elementFactory) {
        return elementFactory.createClassFromText(generateContributeClass(bindClassName), mClass).getInnerClasses()[0];
    }

    private String generateContributeClass(String BindClassName) {
        StringBuilder sb = new StringBuilder("@Module\n" +
                "public abstract class CityStoreBuilder {}");
        return sb.toString();
    }


    /**
     * 创建绑定方法
     */
    private String generateContributeMethod(String bindClassName) {
        StringBuilder sb = new StringBuilder("@ContributesAndroidInjector(modules = {CommandModule.class})\n" +
                "    abstract " + bindClassName + " " + getContributeMethodName(bindClassName) + "();");

        return sb.toString();
    }

    @NotNull
    private String getContributeMethodName(String bindClassName) {
        return METHOD_PRE + bindClassName;
    }

    private static void findAndRemoveMethod(PsiClass clazz, String methodName, String... arguments) {
        // Maybe there's an easier way to do this with mClass.findMethodBySignature(), but I'm not an expert on Psi*
        PsiMethod[] methods = clazz.findMethodsByName(methodName, false);

        for (PsiMethod method : methods) {
            PsiParameterList parameterList = method.getParameterList();

            if (parameterList.getParametersCount() == arguments.length) {
                boolean shouldDelete = true;

                PsiParameter[] parameters = parameterList.getParameters();

                for (int i = 0; i < arguments.length; i++) {
                    if (!parameters[i].getType().getCanonicalText().equals(arguments[i])) {
                        shouldDelete = false;
                    }
                }

                if (shouldDelete) {
                    method.delete();
                }
            }
        }
    }
}
