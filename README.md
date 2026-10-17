# 1. Zasada działania i zastosowania

Algorytmy ewolucyjne to grupa metod optymalizacyjnych inspirowanych mechanizmami ewolucji biologicznej, takimi jak dobór naturalny, dziedziczenie i mutacja. Ich celem jest poszukiwanie możliwie najlepszego rozwiązania danego problemu poprzez stopniowe ulepszanie populacji potencjalnych rozwiązań. W przeciwieństwie do wielu klasycznych metod optymalizacyjnych algorytmy ewolucyjne nie wymagają znajomości pochodnych funkcji celu i mogą być stosowane w problemach o dużej przestrzeni poszukiwań. [1,2]

Jednym z najpopularniejszych rodzajów algorytmów ewolucyjnych jest algorytm genetyczny (Genetic Algorithm, GA). Wykorzystuje on populację osobników, z których każdy reprezentuje potencjalne rozwiązanie problemu. Informacje o rozwiązaniu zapisywane są w postaci chromosomu składającego się z genów. Sposób reprezentacji chromosomu zależy od rozwiązywanego problemu i może przyjmować postać ciągu binarnego, zbioru liczb rzeczywistych lub permutacji elementów.[1]

Działanie algorytmu genetycznego opiera się na cyklicznym wykonywaniu następujących etapów:

- Inicjalizacja populacji - utworzenie początkowego zbioru osobników, najczęściej poprzez losowe wygenerowanie potencjalnych rozwiązań.
- Ocena przystosowania (fitness) - obliczenie wartości funkcji oceny dla każdego osobnika, która określa jakość reprezentowanego przez niego rozwiązania.
- Selekcja - wybór osobników przeznaczonych do reprodukcji. Osobniki o lepszych wynikach mają zazwyczaj większe prawdopodobieństwo wyboru.
- Krzyżowanie (crossover) - łączenie informacji genetycznej wybranych rodziców w celu utworzenia nowych osobników, które dziedziczą ich cechy.
- Mutacja (mutation) - wprowadzanie losowych zmian w chromosomach, co pozwala zwiększyć różnorodność populacji i ograniczyć ryzyko przedwczesnej zbieżności.
- Tworzenie nowej populacji - zastąpienie części lub wszystkich osobników nowym pokoleniem. Możliwe jest również zastosowanie elitaryzmu, czyli zachowania najlepszych osobników z poprzedniego pokolenia.

Proces ten jest powtarzany do momentu spełnienia określonego warunku zakończenia, którym może być osiągnięcie maksymalnej liczby pokoleń, uzyskanie oczekiwanej wartości funkcji oceny lub brak poprawy wyników przez określoną liczbę pokoleń. [1, 2]

Algorytmy ewolucyjne znajdują zastosowanie w wielu dziedzinach informatyki i inżynierii. Do wybranych obszarów ich wykorzystania należą:

- Optymalizacja funkcji matematycznych - wyznaczanie minimum lub maksimum funkcji, szczególnie w przypadkach, w których zastosowanie klasycznych metod jest utrudnione.
- Problemy logistyczne i transportowe - wyznaczanie tras pojazdów, planowanie dostaw oraz rozwiązywanie problemu komiwojażera (Traveling Salesman Problem, TSP).
- Planowanie i harmonogramowanie - optymalizacja kolejności wykonywania zadań, przydziału zasobów oraz harmonogramów produkcyjnych.
- Inżynieria i projektowanie - dobór parametrów konstrukcji i urządzeń w celu zmniejszenia kosztów, masy lub zużycia energii.
- Sztuczna inteligencja i uczenie maszynowe - optymalizacja hiperparametrów modeli, dobór cech oraz poszukiwanie odpowiednich struktur modeli.

Główną zaletą algorytmów ewolucyjnych jest możliwość znajdowania dobrych rozwiązań złożonych problemów bez konieczności przeszukiwania całej przestrzeni rozwiązań. Ich wadą jest natomiast brak gwarancji znalezienia optimum globalnego oraz zależność uzyskiwanych wyników od parametrów algorytmu, takich jak wielkość populacji czy prawdopodobieństwo mutacji i krzyżowania. Ich wadą jest natomiast brak gwarancji znalezienia optimum globalnego. Algorytmy ewolucyjne mają charakter heurystyczny i w praktyce często prowadzą do uzyskania rozwiązania bardzo dobrego, lecz niekoniecznie najlepszego spośród wszystkich możliwych rozwiązań. Ze względu na elementy losowe kolejne uruchomienia tego samego algorytmu mogą prowadzić do uzyskania różnych wyników.

W niniejszym projekcie zastosowano algorytm genetyczny do rozwiązania problemu komiwojażera.

## Problem komiwojażera (TSP)

Problem komiwojażera (ang. Traveling Salesman Problem, TSP) jest jednym z klasycznych problemów optymalizacji kombinatorycznej. Polega na wyznaczeniu możliwie najkrótszej trasy łączącej określoną liczbę miast, przy założeniu, że każde miasto musi zostać odwiedzone dokładnie jeden raz, a po odwiedzeniu wszystkich miast następuje powrót do miasta początkowego.

Problem ten można przedstawić za pomocą grafu ważonego, w którym wierzchołki reprezentują miasta, natomiast krawędzie odpowiadają połączeniom pomiędzy nimi. Każdej krawędzi przypisana jest waga określająca odległość lub koszt przemieszczenia się pomiędzy dwoma miastami.

Wyróżnia się dwa podstawowe warianty problemu komiwojażera:

- Symetryczny (STSP) – odległość pomiędzy dwoma miastami jest taka sama niezależnie od kierunku przemieszczania się.
- Asymetryczny (ATSP) – koszt przemieszczenia się z miasta A do miasta B może różnić się od kosztu podróży w przeciwnym kierunku.

W niniejszym projekcie rozpatrywany jest symetryczny problem komiwojażera, w którym miasta są reprezentowane przez współrzędne geograficzne, a odległości wyznaczane są wzorem haversine.

Całkowita długość trasy jest sumą odległości pomiędzy kolejnymi odwiedzanymi miastami, z uwzględnieniem powrotu do miasta początkowego. Celem optymalizacji jest znalezienie takiej kolejności odwiedzania miast, dla której suma tych odległości będzie możliwie najmniejsza.

Przykładowo dla czterech miast A, B, C i D jedną z możliwych tras jest:

`A → B → C → D → A`

Innym rozwiązaniem może być trasa:

`A → C → B → D → A`

Obie trasy spełniają warunki problemu, jednak ich całkowite długości mogą się różnić. Zadaniem algorytmu jest znalezienie rozwiązania o jak najmniejszej długości.

Główną trudnością problemu komiwojażera jest bardzo szybki wzrost liczby możliwych tras wraz ze wzrostem liczby miast. Dla symetrycznego problemu z n miastami liczba różnych tras, przy utożsamieniu cyklicznych przesunięć i tras przebieganych w przeciwnych kierunkach, wynosi (n - 1)! / 2.

Problem komiwojażera należy do klasy problemów NP-trudnych. Oznacza to, że nie jest znany algorytm, który dla każdego przypadku ogólnego TSP gwarantowałby znalezienie rozwiązania optymalnego w czasie wielomianowym. Z tego powodu dla większych instancji często stosuje się algorytmy ewolucyjne.W przypadku zastosowania algorytmu genetycznego do problemu komiwojażera pojedynczy osobnik może reprezentować jedną możliwą trasę, zapisaną jako permutacja miast. Funkcja przystosowania określa całkowitą długość tej trasy, a ponieważ problem ma charakter minimalizacyjny, lepszym rozwiązaniem jest osobnik reprezentujący krótszą trasę. Operacje selekcji, krzyżowania i mutacji pozwalają następnie tworzyć kolejne populacje potencjalnych tras i poszukiwać coraz krótszych rozwiązań.

## 2. Biblioteki programistyczne

[opis wybranych bibliotek programistycznych: instalacja, możliwości, dostępne algorytmy, zalety i wady. Należy opisać przynajmniej 2 biblioteki: DEAP oraz Jenetics – można uwzględnić również inne.]

## 3. Zaimplementowany algorytm rozwiązujący wybrany problem optymalizacyjny

[opis wybranego problemu optymalizacyjnego (np. optymalizacja funkcji, komiwojażer, generowanie optymalnych konstrukcji (np. http://www.boxcar2d.com/), optymalizacja wielokryterialna, wielokryterialna optymalizacja trasy drona/statku z uwzględnieniem przeszkód/pogody) oraz szczegółowy opis algorytmu ewolucyjnego użytego do jego rozwiązania: pseudokod wraz z opisem słownym, sposób reprezentacji rozwiązania w genotypie osobnika, wartości parametrów (liczebność populacji, prawdopodobieństwa mutacji i rekombinacji oraz inne w zależności od użytego algorytmu) wraz z komentarzami]

## 4. Wyniki eksperymentów

[wartości parametrów algorytmu ustawiane dla poszczególnych eksperymentów, jeżeli różnią się od tych podanych w sekcji 3, skomentowane wykresy pokazujące średnie wyniki z odchyleniami standardowymi z co najmniej 10 uruchomień algorytmu (wykresy powinny pokazywać: najlepszy, najgorszy i średni fitness w populacji w trakcie trwania eksperymentu), omówienie najlepszego znalezionego rozwiązania.]

## 5. Podsumowanie

[podsumowanie rezultatów projektu oraz uzyskanych wyników eksperymentów]

## Bibliografia

1. Zhi-Hua Zhou, Yang Yu, Chao Qian, Evolutionary Learning: Advances in Theories and Algorithms, Springer, 2019
2. Devin Soni, Introduction to Evolutionary Algorithms, https://towardsdatascience.com/introduction-to-evolutionary-algorithms-a8594b484ac, Dostęp: 15.03.2021