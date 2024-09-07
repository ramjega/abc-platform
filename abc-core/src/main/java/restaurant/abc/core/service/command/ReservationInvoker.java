package restaurant.abc.core.service.command;

import java.util.ArrayList;
import java.util.List;

public class ReservationInvoker {
    private List<ReservationRequest> reservationCommands = new ArrayList<>();

    public void addCommand(ReservationRequest command) {
        reservationCommands.add(command);
    }

    public void executeCommands() {
        for (ReservationRequest command : reservationCommands) {
            command.execute();
        }
        reservationCommands.clear();
    }
}
