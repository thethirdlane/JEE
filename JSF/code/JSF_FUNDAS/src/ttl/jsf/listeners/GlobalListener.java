package ttl.jsf.listeners;

import javax.faces.bean.ManagedBean;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Named;

@Named
public class GlobalListener implements PhaseListener {
    @Override
    public void beforePhase(PhaseEvent event) {
        System.out.println("GlobalPhaseListener Before: " + event.getPhaseId() + ", source: " + event.getSource());
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        System.out.println("GlobalPhaseListener After: " + event.getPhaseId() + ", source: " + event.getSource() + "\n");
        if(event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            System.out.println("\n*****************************\n");
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }


    public void viewBeforePhase(PhaseEvent event) {
        System.out.println("\tViewBeforePhase: " + event.getPhaseId() + ", source: " + event.getSource());
    }

    public void viewAfterPhase(PhaseEvent event) {
        System.out.println("\tViewAfterPhase: " + event.getPhaseId() + ", source: " + event.getSource());
    }

}
