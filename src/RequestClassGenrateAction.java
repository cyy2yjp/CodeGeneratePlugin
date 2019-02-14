import com.intellij.openapi.actionSystem.AnActionEvent;
import generator.ICodeGenerator;
import generator.impl.RequestGenerate;
import org.jetbrains.annotations.NotNull;

public class RequestClassGenrateAction extends AbstractAnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        ICodeGenerator codeGenerator = new RequestGenerate(getPsiClassFromContext(e));
        codeGenerator.generate();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        setEnabled(e);
    }
}
