"""
1. En statistisk språkmodell lar oss beregne sannsynligheten for en gitt setning
   eller predikere hva som er neste ord i en sekvens.
   vi bruker korpusfrekvens for å beregne sannsynligheten for sekvenser av ord.


Anvendelser for språkmodeller:
    -Maskinoversettelse
    -Prediksjon
    -Ordklassetagging
    -Håndskriftgjenkjenning og OCR (Optical Character Recognition)
    -Talegjenkjenning


2. 1. Bigrammer: (<s>, Per), (Per, synger), (synger, ikke), (ikke, </s>)
                 (<s>, Kari), (Kari, synger), (synger, </s)
                 (<s>, Ola), (Ola, synger), (synger, ikke), (ikke, </s>)

   2. Eksempel: P(synger|Per) = Count(Per, synger) / Count(Per)
                              = 1/1

   3. “<s> Kari synger ikke <\s>”.
      P(Kari|<s>) = Count(<s>, Kari) / Count(<s>) = 1 / 3 = 0.33
      P(synger|Kari) = Count(Kari, synger) / Count(Kari) = 1 / 1 = 1
      P(ikke|synger) = Count(synger, ikke) / Count(synger) = 2 / 3 = 0.67
      P(</s>|ikke) = Count(ikke, </s>) / Count(ikke) = 2 / 2 = 1


      Totale sannsynligheten for setningen:
      P(Kari|<s>)P(synger|Kari)P(ikke|synger)P(</s>|ikke)
      = 0.33 x 1 x 0.67 x 1
      = 0.2211


"""
#3
from collections import Counter
import nltk
from nltk.corpus import gutenberg

#print(gutenberg.fileids())


moby_words = gutenberg.words("melville-moby_dick.txt")
total_words = len(moby_words)

distinct_words = []
for word in moby_words:
    distinct_words.append(word.lower())

total_distinct_words = len(set(distinct_words))


#3.1
print("\n1. Total antall tokens: ", total_words)
#3.2
print("2. Total antall ord-typer: ", (total_distinct_words))

#3.3
fd_moby_words = Counter(moby_words)
print("\n3. Most common words: ", fd_moby_words.most_common(20))

#3.4
print("\n4. Frequency of 'peace': ", fd_moby_words["peace"])
print("   Frequency of 'whale' :", fd_moby_words["whale"])
print("   Frequency of 'boy' :", fd_moby_words["boy"])


probabilities = {}
for word, count in fd_moby_words.items():
    probabilities[word] = count / total_words


import numpy as np
text = np.random.choice(list(probabilities.keys()), 20,
                        p=list(probabilities.values()))
#print(text)
word_probabilities = []
for w in text:
    word_probabilities.append(probabilities[w])
#print(np.prod(word_probabilities))

#3.5
moby_sents = gutenberg.sents("melville-moby_dick.txt")
from nltk import bigrams, trigrams
print("\n5. Bigrammer til den fjerde setningen: \n  " , list(bigrams(moby_sents[3], pad_left = True, pad_right = True)))

#3.6
print("\n6. Trigrammer til den femte setningen: \n  " , list(trigrams(moby_sents[4], pad_left = True, pad_right = True)))

#3.7
from collections import defaultdict
bigram_counts = defaultdict(lambda: defaultdict(lambda: 0))
bigram_model = defaultdict(lambda: defaultdict(lambda: 0.0))

for sentence in moby_sents:
    for w1, w2 in bigrams(sentence, pad_right=True, pad_left=True):
            bigram_counts[w1][w2] += 1


for w1 in bigram_counts:
    total_bigramcount = sum(bigram_counts[w1].values())
    for w2 in bigram_counts[w1]:
        bigram_model[w1][w2] = bigram_counts[w1][w2]/total_bigramcount
