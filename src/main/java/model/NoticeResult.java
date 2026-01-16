package model;

public class NoticeResult {
    private boolean notice;
    private int minutes;
    private boolean sessionExpired;

    // 通常通知用
    public NoticeResult(boolean notice, int minutes) {
        this.notice = notice;
        this.minutes = minutes;
        this.sessionExpired = false;
    }

    // セッション切れ用
    public NoticeResult(boolean sessionExpired) {
        this.sessionExpired = sessionExpired;
        this.notice = false;
        this.minutes = 0;
    }

    // 手書きでJSON化
    public String toJson() {
        if (sessionExpired) {
            return "{\"sessionExpired\":true}";
        }
        return "{\"notice\":" + notice + ",\"minutes\":" + minutes + "}";
    }

    // getterは必要なら
    public boolean isNotice() {
        return notice;
    }

    public int getMinutes() {
        return minutes;
    }
}
