## Sketchbook

* pokazać, że albo coś jest sytuacją wyjątkowoą albo deklarujemy throws
* najprostsza forma takiego przypadku to byłaby zamiana miejsc gdzie występuje null na 
 throw bisuness exception, ale tutaj rozszerzam o nieco bardziej szczegółowe przyczyny niepoprawności PESEL
    * Nie pasuje wzorzec regexp (nie patrzę co nie pasuje we wzorcu, po prostu nie pasuje)
    * nie zgadza się checksuma
    * zakodowana data jet błędna 
* opisać dlaczego jest IllegalArgumentException - później to zmienimy na guavę
* metodę isChecsumValid napisać błędnie - zwracjącą boolean + wyrzucjącą wyjątek
* wyjątki rzucane w różnych miejscach moga powodować problemy
* wyrzucenie wyjątku przez isChecksumValid(pesel) spowoduje, że linia ``throw new PeselDecodingException("Can not decode information from PESEL " + pesel);`` nigdy nie jest wywoływna
* programujący funkcyjnie nienawidza tego(!)