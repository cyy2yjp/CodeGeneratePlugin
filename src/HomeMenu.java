import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.*;
import generator.impl.ContributesClassGenerator;
import org.jetbrains.annotations.NotNull;

public class HomeMenu extends AbstractAnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiClass psiClass = getPsiClassFromContext(e);
        generateCode(psiClass);
    }

    private void generateCode(final PsiClass psiClass) {
        new ContributesClassGenerator(psiClass).generate();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        //按钮是否可见
        setEnabled(e);
    }
}
