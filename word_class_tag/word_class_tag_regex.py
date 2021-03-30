import nltk
#2.2
brown_news = nltk.corpus.brown.tagged_words(categories="news")


#2.3
print("2.3")
from collections import defaultdict, Counter

tags = defaultdict(list)
for w, pos in brown_news:
    word = w.lower()
    if pos not in tags[word]:
        tags[word].append(pos)


count_tag = 0
antall_flertydige_ord = 0
for key, value in tags.items():
    if len(value) > 1:
        antall_flertydige_ord += 1
    #find the number of maximum tags for a word. to be used for 2.4
    if len(value) > count_tag:
        count_tag = len(value)

print("Antall flertydige ord:",str(antall_flertydige_ord)+".\n")


#2.4
print("2.4")

# to get a list with all the most tagged words.
list_of_mostTagged = []
for word, value in tags.items():
    if len(value) == count_tag:
        list_of_mostTagged.append(word)



print(list_of_mostTagged, "er ordene med flest tagger,", count_tag, "distinkte tagger.\n")
for word in list_of_mostTagged:
    print(word,":",tags[word])




#2.5
print("\n2.5")

antall_forekomster_tags = defaultdict(Counter)
for w, pos in brown_news:
    word = w.lower()
    antall_forekomster_tags[word][pos] += 1



def freqs(w):
    print(w+":", list(antall_forekomster_tags[w].items()))

freqs("run")

#2.6
print("\n2.6")
for word in list_of_mostTagged:
    freqs(word)


#3
print("\n3")
patterns = [
        (r'.*ful$', 'JJ'),                # adjective, i.e. toughtful
        (r'(and|but|if)$', 'CC'),         # conjunction, i.e. and/but/if
        (r'.*ly$', 'RB'),                 # adverb, i.e. calmly
        (r'(Me|me|Him|him|Her|her|Us|us|Them|them)$', 'PPO'), # object pronouns
        (r'(I|i|You|you|He|he|She|she|It|it|We|we|They|they)$', 'PPS'), # subject pronouns
        (r'(the|The)$', 'DET'),           # determinative
        (r'(on|in|at|since|for|before|with|until|by|under|below|over|above|across|into|towards|from|down|up)$', 'PR'), # preposition
        (r'a$', 'AT'),                    # article
        (r'to$', 'TO'),                   # to
        (r'(!|.)$', '.'),                 # period
        (r',$', ','),                     # comma

        (r'.*ing$', 'VBG'),               # gerunds
        (r'.*ed$', 'VBD'),                # simple past
        (r'.*es$', 'VBZ'),                # 3rd singular present
        (r'.*ould$', 'MD'),               # modals
        (r'.*\'s$', 'NN$'),               # possessive nouns
        (r'.*s$', 'NNS'),                 # plural nouns
        (r'^-?[0-9]+(.[0-9]+)?$', 'CD'),  # cardinal numbers
        (r'.*', 'NN'),                    # nouns (default)
        ]

regexp_tagger = nltk.RegexpTagger(patterns)


from nltk.corpus import brown


# adventure_sentences = brown.sents(categories=["adventure"])
# for sentence in adventure_sentences:
#     print(regexp_tagger.tag(sentence))


brown_tagged_adventure = brown.tagged_sents(categories = 'adventure')
print("Adventure category evaluation:",regexp_tagger.evaluate(brown_tagged_adventure))

brown_tagged_fiction = brown.tagged_sents(categories = 'fiction')
print("Fiction category evaluation:",regexp_tagger.evaluate(brown_tagged_fiction),"\n")






f = open("setninger.txt", "r")

for line in f:
    list_words = line.split()
    print(regexp_tagger.tag(list_words))


f.close()
