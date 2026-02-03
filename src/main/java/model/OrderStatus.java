package model;

public enum OrderStatus {
	/** 注文済み */
    ORDERED(1),
    
    /** 調理済み */
    READY(2),
    
    /** 完了（配膳済み） */
    DELIVERED(3);

    private final int id;

    // コンストラクタ.
    OrderStatus(int id) {
        this.id = id;
    }

    // IDを取得するメソッド.
    public int getId() {
        return id;
    }
    public static OrderStatus fromId(int id) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("不正なステータスID: " + id);
    }
}
