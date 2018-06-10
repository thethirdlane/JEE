package ttl.jsf.listeners;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Named;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
public class TreeViewListener implements PhaseListener {
    @Override
    public void beforePhase(PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.RESTORE_VIEW || event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            System.out.println("TreeViewListener Before: " + event.getPhaseId() + ", source: " + event.getSource());
            FacesContext fc = FacesContext.getCurrentInstance();
            UIViewRoot viewRoot = fc.getViewRoot();
            String tree = print(viewRoot);
            System.out.println(tree);

        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.RESTORE_VIEW || event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            System.out.println("TreeViewListener After: " + event.getPhaseId() + ", source: " + event.getSource());
            FacesContext fc = FacesContext.getCurrentInstance();
            UIViewRoot viewRoot = fc.getViewRoot();
            String tree = print(viewRoot);
            System.out.println(tree);
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }

    private String print(UIComponent root) {
        return print(root, 0).toString();

    }

    private StringBuilder print(UIComponent root, int depth) {
        StringBuilder sb = new StringBuilder(1000);
        if (root == null) {
            sb.append("ViewRoot is null:").append(root);
        } else {
            String indent = Stream.generate(() -> "  ").limit(depth).collect(Collectors.joining());
            sb.append(indent).append(root.getClass().getSimpleName()).append(", id: " + root.getClientId());
            if(root instanceof ValueHolder) {
                Object value = ((ValueHolder)root).getValue();
                sb.append(", value: " + value);
            }
            sb.append("\n");

            //recurse on each of the children
            root.getChildren().stream().forEach(comp -> {
                sb.append(print(comp, depth + 1));
            });
        }
        return sb;
    }

    public void viewBeforePhase(PhaseEvent event) {
        System.out.println("\tViewBeforePhase: " + event.getPhaseId() + ", source: " + event.getSource());
    }

    public void viewAfterPhase(PhaseEvent event) {
        System.out.println("\tViewAfterPhase: " + event.getPhaseId() + ", source: " + event.getSource());
    }

}
