package action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ItemDao;
import model.Item;
import model.Option;

public class ItemUpdateConfirmAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// セッションの取得（なければ新規作成、あれば既存ものを返す）.
		HttpSession session = request.getSession();

		// セッションに編集中の商品オブジェクトを格納する.
		Item item = (Item) (session.getAttribute("editItem"));

		// パラメータの受け取り.
		String name = request.getParameter("name");
		int price = Integer.parseInt(request.getParameter("price"));
		String image = request.getParameter("image");
		int orderNumber = Integer.parseInt(request.getParameter("order_number"));
		int categoryId = Integer.parseInt(request.getParameter("category"));
		boolean stock = Boolean.parseBoolean(request.getParameter("stock"));
		String[] optionIdsStr = request.getParameterValues("option");
		List<Option> optionList = new ArrayList<>();

		try {
			var dao = new ItemDao();

			// オプションの変換.
			if (optionIdsStr != null) {
				List<Integer> optionIdList = new ArrayList<>();
				optionIdList = Arrays.stream(optionIdsStr)
						.map(Integer::parseInt)
						.collect(Collectors.toList());

				optionList = dao.searchOptionByOptionIdList(optionIdList);
			}

			// カテゴリー名の取得.
			String categoryName = dao.getCategoryList().get(categoryId);

			// 商品オブジェクトに受け取ったパラメータを入れる.
			item.setName(name);
			item.setPrice(price);
			item.setItemNo(orderNumber);
			item.setCategoryId(categoryId);
			item.setCategory(categoryName);
			item.setStock(stock);
			item.setOptionList(optionList);

			var imagePart = request.getPart("image");
			if (imagePart != null && imagePart.getSize() != 0)
				item.setImage(image);

		} catch (Exception e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のメッセージ.
			request.setAttribute("errMsg", e.getMessage());
		}

		return "/admin/modify_update_confirm.jsp";
	}

}
