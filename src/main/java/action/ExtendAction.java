package action;

import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.http.HttpSession;

import dao.RoomDao;
import model.Room;

public class ExtendAction {

    public void execute(int extendMinutes, HttpSession session) throws Exception {

        Room sessionRoom = (Room) session.getAttribute("room");
        if (sessionRoom == null) throw new IllegalStateException("セッションに部屋情報がありません");

        // --- DBから予定時間を取得 ---
        Room room = RoomDao.getRoomById(sessionRoom.getId());
        if (room == null) throw new IllegalStateException("部屋情報がDBに存在しません");

        // 実際受付時間と予定受付時間の差分を計算
        LocalTime actualReception = room.getReceptionTime().toLocalTime();
        LocalTime plannedReception = room.getRes_receptionTime().toLocalTime();
        LocalTime plannedLeaving = room.getRes_leavingTime().toLocalTime();

        long diffMinutes = ChronoUnit.MINUTES.between(plannedReception, actualReception);

        // 差分＋延長分を加算
        LocalTime adjustedLeaving = plannedLeaving.plusMinutes(diffMinutes + extendMinutes);

        // セッションと DB に反映
        Time newLeavingTime = Time.valueOf(adjustedLeaving);
        room.setLeavingTime(newLeavingTime);

        RoomDao.updateLeavingTime(room.getId(), newLeavingTime);

        // 通知リセット
        session.removeAttribute("notice15Shown");
        session.removeAttribute("notice10Shown");

        // セッションに再セット
        session.setAttribute("room", room);
    }
}

