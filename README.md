# Spidy

Simple web-crawler in Kotlin

```kotlin
"bbc.co.uk".crawl()
```

Prints the following:

```
Crawling bbc.co.uk concurrently
0:  200 /
1:   200 /accessibility/
2:   200 /news/business-49330150
3:   200 /news/world-europe-49345912
4:   200 /sport
5:   200 /sounds/play/p07khx4v
6:   200 /sport/football/49384772
7:   200 /sport/av/football/49382956
8:   200 /programmes/p07kdmj0
9:    200 /usingthebbc/privacy/
10:   200 /news/uk-england-norfolk-49355814
11:    200 /radio4/features/you-and-yours/disability/
12:     200 /usingthebbc/terms/do-i-need-a-tv-licence/
13:     -1 ?start
14:   200 /news/uk-england-kent-49389873
15:    200 /accessibility/best_practice/redesign_feedback.shtml
16:    200 /accessibility/best_practice/
17:     200 /usingthebbc/privacy/cy/
18:     200 /usingthebbc/privacy/keeping-my-children-safe-online/
19:    200 /accessibility/on_the_bbc/news_updates.shtml
20:   200 /news/business-49355057
21:    200 /accessibility/best_practice/what_is.shtml
22:    200 /ouch/
23:    200 /news/av/uk-england-suffolk-49206602/i-turned-drawing-doodles-into-a-career
24:   200 /news/world-asia-49380633
25:   200 /news/uk-england-northamptonshire-49359470
26:   200 /news/entertainment-arts-49389134
27:     200 /usingthebbc/privacy-policy/
28:     200 /usingthebbc/privacy/how-does-the-bbc-collect-data-about-me/
29:   200 /sport/football/49386282
30:      200 /usingthebbc/privacy/keeping-my-children-safe-online/cy/
31:      200 /usingthebbc/privacy/how-does-the-bbc-collect-data-about-me/cy/
32:      200 /usingthebbc/privacy/what-are-my-rights/cy/
33:      200 /usingthebbc/privacy/privacy-promise/cy/
34:    200 /blogs/bbcinternet/2013/01/news_connected_studio.html
35:     200 /usingthebbc/privacy/gd/
36:     200 /usingthebbc/privacy/privacy-notices/
37:    200 /news/av/uk-england-wiltshire-49348272/the-village-for-recovering-veterans
38:     200 /usingthebbc/terms/can-i-use-bbc-content/
39:      200 /usingthebbc/privacy/how-do-apps-capture-my-data/cy/
40:      200 /usingthebbc/privacy/how-long-will-the-bbc-keep-my-information/cy/
41:     200 /news/uk-17500000
42:       200 /usingthebbc/cookies/how-can-i-change-my-bbc-cookie-settings/cy/
43:     200 /usingthebbc/privacy/do-you-share-my-data/
44:      200 /usingthebbc/privacy-policy/cy/
45:       200 /usingthebbc/cookies/cy/
46:     404 /mailto:?subject=Shared%20from%20BBC%20News&body=https%3A%2F%2Fwww.bbc.co.uk%2Fnews%2Fav%2Fuk-england-wiltshire-49348272%2Fvillage-in-wiltshire-opens-for-military-veterans
47:    404 /fb-messenger://share?app_id=58567469885&redirect_uri=https%3A%2F%2Fwww.bbc.co.uk%2Fnews%2Fuk-england-kent-49389873&link=https%3A%2F%2Fwww.bbc.co.uk%2Fnews%2Fuk-england-kent-49389873%3FCMP%3Dshare_btn_me
48:       200 /usingthebbc/account/cy/
49:     200 /usingthebbc/privacy/how-long-will-the-bbc-keep-my-information/
```

... and much more