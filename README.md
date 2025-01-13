# Directory Service Projesi

Bu proje, bir Spring Boot tabanlı bir `Directory Service` uygulamasıdır.

## Gereksinimler

Aşağıdaki yazılımların kurulu olduğundan emin olun:

1. **Java Development Kit (JDK) - Sürüm 18**
   - Spring Boot uygulamasını çalıştırmak için JDK sürüm 18 gereklidir.
2. **Maven**
   - Projenin bağımlılıklarını yönetmek ve projeyi derlemek için Maven kullanılmaktadır.
3. **Git** (opsiyonel)
   - Projeyi bir Git deposundan klonlayacaksanız gereklidir.

### Gerekli Araçlar

Proje üzerinde çalışırken aşağıdaki editör veya IDE'ler önerilir:
- **IntelliJ IDEA** (Ultimate Edition tercih edilir).
- Alternatif olarak VS Code ya da Eclipse kullanılabilir.

## Projeyi Çalıştırma

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

2. `src/main/resources/application.properties` veya `application.yml` dosyasını düzenleyerek projenin ihtiyaç duyduğu çevresel ayarları yapabilirsiniz (örneğin, veri tabanı bağlantısı gibi).

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

Varsayılan olarak uygulama aşağıdaki adreste çalışır:
