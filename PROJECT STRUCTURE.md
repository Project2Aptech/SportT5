**1. CẤU TRÚC TÍNH NĂNG** 
- Dự án xoay quanh 3 đối tượng:
	- [*] *Người mua:* Tìm kiếm, nghe thử nhạc, thêm vào giỏ hàng và thanh toán.
	- [S] *Người bán/Người đăng:* Tải nhạc lên, quản lý bản quyền, xem doanh thu và rút tiền.
	- [I] *Quản trị viên (Admin):* Kiểm duyệt bài đăng, quản lý tính năng.
**2. CẤU TRÚC DỮ LIỆU**
- Cần các bản dữ liệu quan trọng:
	 - Users
	 - Posts
	 - Orders
	 - Transactions
	 - Songs
	 - Playlists
	 - Albums
	 - ...
**3. CẤU TRÚC KỸ THUẬT**
- Frontend:
	- Trình phát nhạc
	- Giao diện tìm kiếm bài
	- Dashboard cho nghệ sĩ để quản lý sản phẩm
- Backend: 
	- Lưu trữ file nhạc
	- Cổng thanh toán
**4. QUY TRÌNH XỬ LÝ CHUNG**
- [i] *Đăng nhập / Đăng kí:*
	- Thực hiện đăng kí: Nếu là nghệ sĩ phải đóng tiền
	- Nghệ sĩ sẽ có thêm Dashboard để quản lý các sản phẩm đã đăng
- [u] *Nghệ sĩ:* 
	- Upload file nhạc
	- Server xử lý: Nén file nhạc thành preview (nếu cần) để giảm băng thông
	- Lưu trữ vào database
- [b] *Người mua:*
	- Tìm kiếm / Lướt trên danh sách cho sẵn
	- Bài đăng: Chứa nhạc preview, chi tiết về nghệ sĩ, bài nhạc, giá tiền
	- Cho vào giỏ hàng, thực hiện thanh toán