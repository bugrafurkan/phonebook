
# Phonebook Uygulaması
Spring Boot tabanlı bir telefon defteri uygulaması.

Bu proje, bir Spring Boot tabanlı iki ana servis içeren bir `Phonebook` uygulamasıdır: **Directory Service** ve **Report Service**.

## Gereksinimler

Aşağıdaki yazılımların kurulu olduğundan emin olun:

1. **Java Development Kit (JDK) - Sürüm 18**
   - Spring Boot uygulamasını çalıştırmak için JDK sürüm 18 gereklidir.
   - Ana sınıf: `com.example.directoryservice.DirectoryServiceApplication`
2. **Maven**
   - Projenin bağımlılıklarını yönetmek ve projeyi derlemek için Maven kullanılmaktadır.
3. **Git** (opsiyonel)
   - Projeyi bir Git deposundan klonlayacaksanız gereklidir.
### Veri Tabanı Gereksinimleri
- Bu proje iki MySQL veritabanı kullanır:
  1. **Directory Service** için: Kullanıcı ve adres bilgilerini yönetir.
  2. **Report Service** için: Oluşturulan raporların durum bilgilerini yönetir.
- MySQL’in kurulu olduğundan ve çalışır durumda olduğundan emin olun.

  - Directory Service için varsayılan bağlantı bilgileri:
    - Host: `localhost`
    - Port: `3306`
    - Veritabanı Adı: `directory_db`
    - Kullanıcı: `root`
    - Şifre: `password`
  - Report Service için varsayılan bağlantı bilgileri:
    - Host: `localhost`
    - Port: `3307`
    - Veritabanı Adı: `report_db`
    - Kullanıcı: `root`
    - Şifre: `password`
- Bağlantı ayarlarını yukarıdaki örnek `application.yml` dosyasına uygun olarak düzenleyebilirsiniz.
### Gerekli Araçlar

Proje üzerinde çalışırken aşağıdaki editör veya IDE'ler önerilir:
- **IntelliJ IDEA** (Ultimate Edition tercih edilir).
- Alternatif olarak VS Code ya da Eclipse kullanılabilir.

## Projeyi Çalıştırma
Projeyi çalıştırmadan önce şu adımları tamamladığınızdan emin olun:
1. Veritabanı bağlantı ayarlarınızı yapılandırın (örneğin, `application.yml`).
2. IDE’nizdeki doğru JDK sürümünü seçtiğinizden emin olun.
### Servisler
- **Directory Service**: Telefon defteri kayıtlarını yönetmek için kullanılır. Kullanıcılar, kişileri ve adreslerini tanımlayabilir.
- **Report Service**: Belirli kriterlere göre rapor oluşturur. Örneğin, belirli bir lokasyonda kaç kişinin bulunduğuna dair rapor hazırlayabilir.

### Directory Service API

#### **Base URL**
```
http://<hostname>:<port>/api/persons
```

#### **API Endpoints**

1. **Create a Person**
   - **URL:** `/api/persons`
   - **Method:** `POST`
   - **Description:** Yeni bir kişi oluşturur.
   - **Request Body:**
     ```json
     {
       "name": "John Doe", 
       "email": "john.doe@example.com", 
       "phone": "123456789"
     }
     ```
   - **Response:**
     - Status: `201 Created`

2. **Get All Persons**
   - **URL:** `/api/persons`
   - **Method:** `GET`
   - **Description:** Tüm kişileri döner.
   - **Response:**
     ```json
     [
       {
         "id": "1",
         "name": "John Doe", 
         "email": "john.doe@example.com", 
         "phone": "123456789"
       },
       ...
     ]
     ```

3. **Get Person by ID**
   - **URL:** `/api/persons/{id}`
   - **Method:** `GET`
   - **Description:** Belirli bir kişiyi döner.
   - **Path Parameters:**
     - `id` (string): Kişinin ID'si.
   - **Response:**
     ```json
     {
      "id": "1",
      "name": "John Doe",
      "email": "john.doe@example.com",
      "phone": "123456789"
    }
    ```

4. **Delete Person by ID**
   - **URL:** `/api/persons/{id}`
   - **Method:** `DELETE`
   - **Description:** ID'ye göre bir kişiyi siler.
   - **Response:**
     - Status: `204 No Content`

5. **Update Person**
   - **URL:** `/api/persons`
   - **Method:** `PUT`
   - **Description:** Bir kişiyi günceller.
   - **Parameters:**
     - Query Parameter: `personalId` (string)
   - **Request Body:**
     ```json
     {
       "contact": {
         "phone": "987654321",
         "email": "new.john.doe@example.com"
       }
     }
     ```
   - **Response:**
     ```json
     {
       "id": "1",
       "name": "John Doe",
       "email": "new.john.doe@example.com",
       "phone": "987654321"
     }
     ```

6. **Get Persons With Contacts**
   - **URL:** `/api/persons/with-contacts`
   - **Method:** `GET`
   - **Description:** Kişileri ve kontaklarını döner.
   - **Response:**
     ```json
     [
       {
         "id": "1",
         "name": "John Doe",
         "contacts": [
           {
             "type": "phone",
             "value": "123456789"
           }
         ]
       }
     ]
     ```

### 1. Depoyu Klonlama

Eğer proje bir Git deposunda barındırılıyorsa, aşağıdaki komutu kullanabilirsiniz:

```bash
git clone <REPO_URL>
```

`<REPO_URL>` yerine projenizin Git URL'sini yazın.

### 2. Projenin Yapılandırılması

Projeyi bir IDE ile açtıktan sonra:
1. Projenin Maven bağımlılıklarının otomatik olarak indirildiğinden emin olun.
   - IntelliJ IDEA kullanıyorsanız, proje açıldığında bağımlılıklar genellikle otomatik olarak çözülür.
   - Eğer bağımlılıklar çözülmediyse, aşağıdaki komut ile çözebilirsiniz:
     ```bash
     mvn clean install
     ```

2. `src/main/resources/application.properties` veya `application.yml` dosyasını düzenleyerek projenin ihtiyaç duyduğu çevresel ayarları yapabilirsiniz (örneğin, Directory Service ve Report Service için veritabanı bağlantıları gibi).
   Örnek `Directory Service` için `application.yml`:
   ```yaml
   server:
     port: 8080

   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/directory_db
       username: root
       password: password
     jpa:
       hibernate:
         ddl-auto: update

   ---
   spring:
     datasource:
       url: jdbc:mysql://localhost:3307/report_db
       username: root
       password: password
     jpa:
       hibernate:
         ddl-auto: update
   ```

### 3. Projeyi Çalıştırma

Spring Boot projesini çalıştırmak için aşağıdaki yöntemlerden birini kullanabilirsiniz:

#### IntelliJ IDEA’yi Kullanarak Çalıştırmak
1. `DirectoryServiceApplication` ana sınıfını bulun (paket yolu: `com.example.directoryservice`).
2. Sağ tıklayın ve "Run 'DirectoryServiceApplication'" seçeneğini seçin.

#### Maven Üzerinden Çalıştırmak
Aşağıdaki Maven komutunu kullanarak projeyi çalıştırabilirsiniz:

```bash
mvn spring-boot:run
```

#### Java Komutuyla Çalıştırmak
Projeyi manuel olarak jar dosyasına dönüştürüp çalıştırmak isterseniz:

1. İlk olarak projeyi aşağıdaki komut ile build edin:
   ```bash
   mvn clean package
   ```
2. Ardından oluşturulmuş jar dosyasını aşağıdaki gibi çalıştırın:
   ```bash
   java -jar target/directory-service-0.0.1-SNAPSHOT.jar
   ```

### 4. Uygulamayı Test Etme

**Directory Service** varsayılan olarak şu adreste çalışır:
- `http://localhost:8080`

**Report Service** varsayılan olarak şu adreste çalışır:
- `http://localhost:8081`

#### Test Aşamaları
1. Swagger kullanarak API dokümantasyonu sorgulayabilirsiniz:
   - Directory Service için: `http://localhost:8080/swagger-ui.html`
   - Report Service için: `http://localhost:8081/swagger-ui.html`

2. Örnek API çağrıları (Postman veya cURL aracılığıyla):
   - Directory Service için:
     ```bash
     curl --location --request GET 'http://localhost:8080/api/v1/contacts'
     ```
   - Report Service için:
     ```bash
     curl --location --request GET 'http://localhost:8081/api/v1/reports'
     ```
3. MySQL üzerinde tabloların oluşturulduğunu kontrol edin (örneğin `directory_db` veritabanında).

Varsayılan olarak uygulama aşağıdaki adreste çalışır:
