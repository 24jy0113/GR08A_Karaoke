package action;

import java.sql.Time;
import java.time.LocalTime;

import jakarta.servlet.http.HttpSession;

import dao.RoomDao;
import model.Room;
import service.RoomTimeService;

public class ExtendAction {

    public void execute(int extendMinutes, HttpSession session) throws Exception {

        Room sessionRoom = (Room) session.getAttribute("room");
        if (sessionRoom == null) {
            throw new IllegalStateException("セッションに部屋情報がありません");
        }

        // --- DBから最新の部屋情報を取得 ---
        Room room = RoomDao.getRoomById(sessionRoom.getId());
        if (room == null) {
            throw new IllegalStateException("部屋情報がDBに存在しません");
        }

        // --- 実際の退室時間を計算（共通ロジック） ---
        LocalTime actualLeaving =
                RoomTimeService.calcActualLeavingTime(room);

        // --- 延長分を加算 ---
        LocalTime extendedLeaving =
                actualLeaving.plusMinutes(extendMinutes);

        // --- DBとセッションに反映 ---
        Time newLeavingTime = Time.valueOf(extendedLeaving);
        room.setLeavingTime(newLeavingTime);
        RoomDao.updateLeavingTime(room.getId(), newLeavingTime);

        // --- 通知フラグをリセット ---
        // 延長後は再度15分・10分通知を出すため
        session.removeAttribute("notice15Shown");
        session.removeAttribute("notice10Shown");

        // --- セッション更新 ---
        session.setAttribute("room", room);
    }
}
