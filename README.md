# baibars-case-study-backend


Proje Kurulumu ve Çalıştırılması
Backend (Spring Boot):

backend klasörüne gidin.

Gerekli bağımlılıkları yüklemek için sağdaki Maven logosuyla açılan menüde lifecycle klasörü içindeki install butonuna tıklamanız gerekmektedir.

Backend'i başlatmak için BaibarsCaseStudyBackendApplication sınıfındaki main fonksiyonunu çalıştırın.

Tomcat sunucusu ayağa kalkana kadar bekleyin.

Frontend (Android):

frontend klasörüne gidin.

Android Studio veya tercih ettiğiniz bir IDE ile projeyi açın.

Gerekli bağımlılıkların yüklendiğinden emin olduktan sonra uygulamayı çalıştırın.

Veriler Dashboard Fragment'inde yer almaktadır. Bu fragment, UAV verilerini kullanıcıya sunan ekrandır.

Sahte Veriler (Fake Data) Üretimi
Backend tarafında, UAV verileri dinamik olarak üretilmektedir. Sahte veriler aşağıdaki şekilde oluşturulmaktadır:

Latitude ve Longitude: Koordinatlar, merkezi bir konumdan (41.015137, 28.979530) rastgele bir şekilde ±0.01 derece değiştirilerek oluşturulur.

Altitude: Yükseklik, 100 ile 200 metre arasında rastgele bir değerle belirlenir.

GPS Koordinatları: Latitude ve Longitude birleşimi, %.6f, %.6f formatında bir string olarak döndürülür.

Flight Time: Uçuş süresi, rastgele bir dakika ve saniye ile oluşturulmuş bir saati HH:mm:ss formatında döndürür.

Bu veriler her API çağrısında dinamik olarak üretilir ve frontend tarafına iletilir.

Kullanılan Teknolojiler ve Kütüphaneler
Frontend:

Retrofit: API isteklerini kolaylaştırmak için kullanıldı.

GsonConverter: JSON verisini Java/Kotlin objelerine dönüştürmek için kullanıldı.

Coroutines: Asenkron işlemler için kullanıldı.

Backend:

Spring Boot: Backend uygulaması için kullanıldı.

PostgreSQL Driver: Veritabanı bağlantısı için eklendi (Ancak projede veritabanı kullanılmamıştır).

Spring Starter Web: Web uygulaması için temel yapı sağladı.

Projeyi iki dosya halinde bulabilirsiniz: Önce backend kısmını, ardından frontend kısmını çalıştırın. Veriler, Dashboard Fragment'inde yer almaktadır.

######Veriler, Coroutines ile birlikte her 3 saniyede bir backend tarafından çekilmektedir.######
