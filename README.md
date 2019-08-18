# Spidy

Simple web-crawler in Kotlin

```kotlin
"bbc.co.uk".crawl()
```

Prints the following:

```
0: 200 /
1: 200 /accessibility/
2: 200 /globalresources/mothballing
3: 200 /news
4: 200 /sport
5: 200 /weather
6: 200 /iplayer
7: 200 /sounds
8: 200 /cbbc
9: 200 /tv/cbbc
10: 200 /cbeebies
11: 200 /tv/cbeebies
12: 200 /food
13: 200 /bitesize
14: 200 /arts
15: 200 /taster
16: 200 /news/localnews
17: 200 /tv
18: 200 /radio
19: 200 /bbcthree
20: 200 /bbcthree/article/2a93b8de-7b51-40f0-a7b8-9ec53fe97db3
21: 200 /bbcthree/article/d9febbb7-0606-40df-aac1-d6e623cdd3f9
22: 200 /bbcthree/article/3bc4877d-6892-47b8-b4ac-5159feb21734
23: 200 /bbcthree/article/544c5686-e3bc-4ac2-b4ca-f91eccefe441
24: 200 /bbcthree/article/004b7960-0fcd-4909-acd9-7b68954522f1
25: 200 /bbcthree/category/box-sets
26: 200 /bbcthree/category/comedy
27: 200 /bbcthree/category/docs
28: 200 /bbcthree/category/sex-relationships
29: 200 /bbcthree/category/health-wellbeing
30: 200 /bbcthree/category/lgbtq
31: 200 /bbcthree/category/news
32: 200 /bbcthree/category/sport
33: 200 /bbcthree/category/ufc
34: 200 /bbcthree/category/drugs
35: 200 /bbcthree/category/crime
36: 200 /bbcthree/category/amazing-humans
37: 200 /bbcthree/category/things-not-to-say
38: 200 /tv/bbcthree/a-z
39: 200 /bbcone
40: 200 /bbctwo
41: 200 /tv/bbcthree
42: 200 /bbcfour
43: 200 /tv/radio1
44: 200 /tv/bbcscotland
45: 200 /tv/bbcnews
46: 200 /tv/bbcparliament
47: 200 /tv/bbcalba
48: 200 /tv/s4c
49: 200 /iplayer/categories/arts/featured
50: 200 /iplayer/categories/cbbc/featured
51: 200 /iplayer/categories/cbeebies/featured
52: 200 /iplayer/categories/comedy/featured
53: 200 /iplayer/categories/documentaries/featured
54: 200 /iplayer/categories/drama-and-soaps/featured
55: 200 /iplayer/categories/entertainment/featured
56: 200 /iplayer/categories/films/featured
57: 200 /iplayer/categories/food/featured
58: 200 /iplayer/categories/history/featured
59: 200 /iplayer/categories/lifestyle/featured
60: 200 /iplayer/categories/music/featured
61: 200 /iplayer/categories/news/featured
62: 200 /iplayer/categories/science-and-nature/featured
63: 200 /iplayer/categories/sport/featured
64: 200 /iplayer/categories/archive/featured
65: 200 /iplayer/categories/audio-described/featured
66: 200 /iplayer/categories/signed/featured
67: 200 /iplayer/categories/northern-ireland/featured
68: 200 /iplayer/categories/scotland/featured
69: 200 /iplayer/categories/wales/featured
70: 200 /iplayer/a-z/a
71: 200 /iplayer/guide
72: 200 /iplayer/watching
73: 200 /iplayer/added
74: 200 /iplayer/recommendations
75: 200 /iplayer/guidance
76: 200 /iplayer/help
77: 404 //www.bbc.co.uk/iplayer
78: 200 /usingthebbc/terms
79: 200 /usingthebbc/
80: 200 /usingthebbc/privacy/
81: 200 /usingthebbc/privacy/what-are-you-doing-with-my-data/
82: 200 /usingthebbc/privacy/privacy-promise/
83: 200 /usingthebbc/privacy/keeping-my-children-safe-online/
84: 200 /usingthebbc/cookies/
85: 200 /usingthebbc/cookies/what-do-i-need-to-know-about-cookies/
86: 200 /usingthebbc/cookies/how-can-i-change-my-bbc-cookie-settings/
87: 200 /usingthebbc/account/
88: 200 /usingthebbc/account/what-is-a-bbc-account/
89: 200 /usingthebbc/account/adding-things-on-the-bbc/
90: 200 /usingthebbc/account/how-is-the-bbc-personalised-to-me/
91: 200 /usingthebbc/terms/
92: 200 /usingthebbc/terms/do-i-need-a-tv-licence/
93: 200 /usingthebbc/terms/what-are-the-rules-for-commenting/
94: 200 /usingthebbc/terms/can-i-use-bbc-content/
95: 200 /usingthebbc/terms/can-i-use-bbc-content-for-my-business/
96: 200 /usingthebbc/terms/screening-the-bbc-proms/
97: 200 /usingthebbc/terms/screening-the-bbc-proms/cy/
98: 200 /newyddion
99: 200 /cymrufyw
100: 200 /cymrufyw/cylchgrawn
```

... and much more