package action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

		// セッションから編集中の商品オブジェクトを取得する.
		Item item = (Item) (session.getAttribute("editItem"));

		// セッションからログイン中のユーザの権限情報を取得する.
		Object rawPermissions = session.getAttribute("permissions");
		Set<String> permissions = (rawPermissions instanceof Set) ? (Set<String>) rawPermissions : new HashSet<>();
		Object rawAdmin = session.getAttribute("isAdmin");
		boolean admin = (boolean) rawAdmin;

		// パラメータの受け取り.
		String name = request.getParameter("name");

		String rawPrice = request.getParameter("price");
		Integer price = rawPrice != null ? Integer.parseInt(rawPrice) : null;

		String rawCategoryId = request.getParameter("category");
		Integer categoryId = rawCategoryId != null ? Integer.parseInt(rawCategoryId) : null;

		String rawStock = request.getParameter("stock");
		Boolean stock = rawStock != null ? Boolean.parseBoolean(rawStock) : null;

		String[] rawOptionIds = request.getParameterValues("option");

		try {
			var dao = new ItemDao();

			List<Option> optionList = new ArrayList<>();
			// オプションの変換.
			if (rawOptionIds != null) {
				List<Integer> optionIdList = new ArrayList<>();
				optionIdList = Arrays.stream(rawOptionIds)
						.map(Integer::parseInt)
						.collect(Collectors.toList());

				optionList = dao.searchOptionByOptionIdList(optionIdList);
			}

			// カテゴリー名の取得.
			String categoryName = dao.getCategoryList().get(categoryId);

			// 商品オブジェクトに受け取ったパラメータを入れる.
			if (permissions.contains("VIEW_CUS") && admin) {
				if (name != null)
					item.setName(name);
				if (price != null)
					item.setPrice(price);
				if (categoryId != null) {
					item.setCategoryId(categoryId);
					item.setCategory(categoryName);
				}
				if (!optionList.isEmpty())
					item.setOptionList(optionList);

				// ファイルの受け取り.
				var imagePart = request.getPart("image");
				// ファイルがある場合.
				if (imagePart != null && imagePart.getSize() != 0) {
					System.out.println(2);
					// ファイル名を取得する.
					String fileName = imagePart.getSubmittedFileName();
					if (fileName != null && !fileName.isEmpty()) {
						System.out.println(3);
						// 物理保存用のパス（これはフルパスが必要）.
						String uploadPath = request.getServletContext().getRealPath("/img/items");

						// 物理保存実行
						imagePart.write(uploadPath + File.separator + fileName);

						// 商品オブジェクトに新しいファイル名を入れる.
						item.setImage(fileName);
					}
				}
			}
			if (null != stock)
				item.setStock(stock);

		} catch (Exception e) {
			// デバッグ用のスタックトレース.
			e.printStackTrace();

			// フロントエンド用のメッセージ.
			request.setAttribute("errMsg", e.getMessage());
		}
		

		if(name==null||name.isEmpty()) {
			request.setAttribute("errMsg", "商品名を入力してください");
			return "modify_update.jsp";
		}

		return "modify_update_confirm.jsp";
	}

}
