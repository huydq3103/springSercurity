# springSercurity
# Managedrink

## Giới thiệu

Managedrink là một ứng dụng quản lý đồ uống được xây dựng bằng Spring Boot. Ứng dụng này cho phép người dùng đăng nhập và quản lý quyền hạn cho các vai trò khác nhau trong hệ thống.

## Các tính năng chính

1. **Trang Đăng Nhập** (sử dụng react js)
   - Người dùng có thể đăng nhập vào hệ thống bằng thông tin đăng nhập của mình. 
   - Hệ thống sử dụng JWT (JSON Web Tokens) để xác thực và duy trì phiên làm việc của người dùng.

2. **Trang Thêm Quyền cho Vai Trò**
   - Quản trị viên có thể thêm và phân quyền cho các vai trò khác nhau trong hệ thống.
   - Quản trị viên có thể gán quyền cho các vai trò và quản lý quyền hạn của người dùng.

## Cài đặt

### Yêu cầu hệ thống

- Java 17
- Maven

### Cài đặt

1. **Clone repository**:
   ```bash
   git clone <URL>
   cd managedrink

#Cấu hình
#Cấu hình cơ sở dữ liệu:

Cập nhật thông tin kết nối cơ sở dữ liệu trong file src/main/resources/application.properties:
${DB_PASSWORD} => cau hình trong biến môi trường của windown

spring.datasource.url=jdbc:mysql://localhost:3306/managedrink
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql= true
jwt.secret = ${JWT_SECRET}
jwt.expiration = ${JWT_EXPIRE}
Cấu hình bảo mật:

Cấu hình JWT và các chính sách bảo mật trong class SecurityConfig.

#Phụ thuộc
#Dự án sử dụng các phụ thuộc sau:

1.Spring Boot Starter Web
2.Spring Boot Starter Data JPA
3.Spring Boot Starter Security
4.MySQL Connector
5.Lombok
6.JWT
7.Log4j

Đóng góp
Nếu bạn muốn đóng góp cho dự án này, vui lòng tạo pull request và mô tả các thay đổi của bạn. Chúng tôi rất mong nhận được sự đóng góp của bạn!

Hy vọng file README này đáp ứng đầy đủ yêu cầu của bạn!


