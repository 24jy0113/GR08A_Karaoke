package dao;

public enum OrderStatus {
	/** 注文済み */
    ORDERED(1),
    
    /** 調理済み */
    READY(2),
    
    /** 完了（配膳済み） */
    DELIVERED(3);

    private final int id;

    // コンストラクタ
    OrderStatus(int id) {
        this.id = id;
    }

    // IDを取得するメソッド
    public int getId() {
        return id;
    }
}
