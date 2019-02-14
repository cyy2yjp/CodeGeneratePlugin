package generator.impl;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import generator.ICodeGenerator;

public abstract class AbstractCodeGenerator implements ICodeGenerator {

    protected final PsiClass mClass;
    protected final PsiElementFactory elementFactory;

    public AbstractCodeGenerator(PsiClass psiClass) {
        this.mClass = psiClass;
        elementFactory = JavaPsiFacade.getElementFactory(mClass.getProject());
        ;
    }


    @Override
    public final void generate() {
        startGenerate();
    }

    protected abstract void startGenerate();
}
