package action;

import java.time.LocalTime;

import dao.RoomDao;
import model.ExtendResult;
import model.Room;
import service.RoomTimeService;

public class ExtendPreviewAction {
	public ExtendResult preview(int roomId, int extendMinutes) throws Exception {
        Room room = RoomDao.getRoomById(roomId);

        LocalTime currentLeaving = RoomTimeService.calcActualLeavingTime(room);
        LocalTime newLeaving = currentLeaving.plusMinutes(extendMinutes);

        return new ExtendResult(currentLeaving, newLeaving);
    }
}
