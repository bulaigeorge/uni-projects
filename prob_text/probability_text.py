
#2
import nltk
from nltk.corpus import conll2000 as con

grammar = """NP: {<DT>?<JJ>*<NN>}
               {<DT>?<CD>*<NNP>+}
               {<DT>?<JJ>*<NNS>+}
               {<CD>*<NNS>}
               {<PRP>}
               {<DT>|<WDT>|<WP>}
               """
#{<DT>?<CD>*<NNP>+} - determiner exists or not, number and proper noun 1..*
#{<DT>?<JJ>*<NNS>+} - determiner exists or not, adjectiv 0..* and substantiv plural
#{<CD>*<NNS>} - 0..* number and substantiv plural
#{<PRP>} -personal pronoun
#{<DT>|<WDT>|<WP>} - determiner, wh-determiner or wh-pronoun



cp = nltk.RegexpParser(grammar)

# sentence = [("the", "DT"), ("little", "JJ"), ("yellow", "JJ"), ("dog", "NN"), ("barked", "VBD"), ("at", "IN"),  ("the", "DT"), ("cat", "NN")]
#
# result = cp.parse(sentence)
#
# result.draw()




training_chunks = con.chunked_sents("train.txt", chunk_types = ["NP"])
# print(training_chunks)

# for elem in training_chunks:
#     print(elem)
# print(cp.evaluate(training_chunks))

test_chunks = con.chunked_sents("test.txt", chunk_types=["NP"])

# print(cp.evaluate(test_chunks))

"""
ChunkParse score:
    IOB Accuracy:  79.4%%
    Precision:     53.2%%
    Recall:        61.8%%
    F-Measure:     57.2%%
"""


#3
f = open("wsd_tren.txt")
#use a dict to count senses
senses = {}
instances = 0

#use a dict of dicts to record feature->class->count
features = {}
for l in f:
    fields = l.split()
    cl = fields[0]
    # count occurrences for sense (class)
    senses[cl] = senses.get(cl,0) + 1
    instances += 1

    #record for each feature how many times it occurs with which class
    for feat in fields[1:]:
        if feat in features:
            features[feat][cl] = features[feat].get(cl,0) + 1
        else:
            features[feat] = {cl:1}
f.close()


# print(senses)
#
# print(features)

#3.1
print("3.1 Sannsynligheten for betydning 'Removing:'",senses["Removing"]/instances)


#3.2
day = features["day"]
print("3.2 P(day|Reading):",day["Reading"] / (day["Removing"] + day["Reading"] + day["Self_motion"]))


#3.3
pReading = senses["Reading"] / instances
pRemoving = senses["Removing"] /instances
pSelf_motion = senses["Self_motion"] / instances

paper = features["paper"]
surface = features["surface"]
towards = features["towards"]

print((paper["Reading"]/senses["Reading"]) * (1/senses["Reading"]) * (1/senses["Reading"]) * pReading, "Reading")
print((paper["Removing"]/senses["Removing"]) * (surface["Removing"]/senses["Removing"]) * (1/senses["Removing"]) * pRemoving, "Removing")
print((paper["Self_motion"]/senses["Self_motion"]) * (surface["Self_motion"]/senses["Self_motion"]) * (towards["Self_motion"]/senses["Self_motion"]) * pSelf_motion, "Self_motion")
