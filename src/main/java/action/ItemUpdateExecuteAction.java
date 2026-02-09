package action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ItemDao;
import model.Item;

public class ItemUpdateExecuteAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// セッションの取得（なければ新規作成、あれば既存ものを返す）.
		HttpSession session = request.getSession();

		// セッションに編集中の商品オブジェクトを格納する.
		Item item = (Item) (session.getAttribute("editItem"));
		
		try {
			ItemDao dao = new ItemDao();
			if(item.getId()<1) {
				dao.addItem(item);
			}else {
				dao.updateItem(item);
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		return "/admin/modify_updated.jsp";
	}

}
