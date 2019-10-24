# LEBPS란?

우리는 대학에 오고 자연스러운 **원어민 영어**를 공부하기 위해서는 **팝송**을 자주 듣거나 미국 드라마를 자주 시청하라는 말을 들은 적이 있습니다.

원어민 영어를 공부하고 싶은 군 장병, 대학생 그리고 많은 사람들을 위해서 **LEBPS**( Learning English By Pop Song)는 팝송을 번역해서  **번역본을 제공**하고  **머신러닝**을 통해 **문법을 분석**하여 중요한 단어를 추출해 단어장을 만들어 제공하는 등 영어공부를 도울 수 있도록 하는 다양한 기능들을 제공합니다.

**(문법을 분석, 중요한 단어를 추출해서 단어장을 만들어 제공하는 기능은 오직 LEBPS에서 제공합니다.)**

# 필수 조건 안내 (Prerequisites)
**SDK version**
compileSdkVersion 28
minSdkVersion 15

**부가 조건** 
LEBPS에는 유튜브를 통해서 음악을 실행 할 수 있는 기능을 지원합니다.
어플리케이션이 실행되는 에뮬레이터나 개인 스마트폰에서 유튜브가 설치되어있어야 해당 기능을 실행 할 수 있으므로 유튜브를 설치하거나 최신버전으로 업데이트 해야합니다.

# 사용법 및 기능

## 1) 음악검색

**메인화면**에서 제목과 아티스트의 정보를 입력해서 음악을 가져올 수 있습니다.

![enter image description here](https://lh3.googleusercontent.com/plfvby97AagmRNOC4tmDVHQU9QcgIn_jDSkMMheTOkt_mHIk81yBfcBfa1JepoeVY3_qq1zZcazI)![enter image description here](https://lh3.googleusercontent.com/93duheQzk_eb7hDgL0-66VJveMWOWE13BfudqWOhtNT0HdT5eiCAg9n6jev3Fs66p1woBsZdPCTV)![enter image description here](https://lh3.googleusercontent.com/5qE-hPsxyeUiAEdQ8k-cHFfxOi-IdGUqIZk9iPeUNw24a0NBB5TahU2r0Worpfy34TfnznO5fdPb)   
**부가기능**

1. 리스트에 있는 음악에서 플레이버튼을 클릭할 경우 자동으로 유튜브에 검색이 되어 음악을 감상 할 수 있습니다.
2. 음악의 제목을 입력하지 않고 아티스트만 입력할 경우 해당 아티스트의 인기있는 음악 10개를 자동으로 선정해서 음악 리스트에 추가 합니다.
3. 제목과 아티스트를 완벽히 일치시키지 않고 비슷하게 입력해도 어플리케이션이 자동으로 파악해서 음악을 추가 할 수 있습니다. 
( 그러나 너무 모호한 입력일 경우 입력내용 확인 매세지가 생성되며 음악은 추가되지 않습니다.)
![enter image description here](https://lh3.googleusercontent.com/H_1Z57eLP0DmhIeRYihGI44jiZGWObhAPuYt_xVfS0Px1azoo8IoLSJ7Y3Oh7fvnoFEgZtLQRxRd)![enter image description here](https://lh3.googleusercontent.com/Y9SwXIhaA6ES9rBfEsoFgEPz-GmHLnH7JMdPV3AA3YxXCMTzqofhml_HeXVQJy5HeiMD7KtN0LG_) ![enter image description here](https://lh3.googleusercontent.com/cS7I_lRxXsFsMwGF9DiNkHYX7HdS9fhseOK6L3Wc7jaGhuoX4YTysGEAS68xJApH3j3n0s6VC8OS) ![enter image description here](https://lh3.googleusercontent.com/4kEhP7-iYPfv9Uji5aC92yhlsXeHYflwi_jA4hL6Q7DZxTWEtQX2q3b8rjB5uKBzjX6gdeEHgVbH)

## 2) 가사, 번역

메인화면의 리스트에서 원하는 음악을 클릭하면 해당 음악에 대한 정보를 보여주는 액티비티로 이동하여 검색한 음악의 원문가사와 **Google Translation API**를 이용해 번역된 가사를 제공합니다. (ex. Maroon5 - Best 4 U)
![enter image description here](https://lh3.googleusercontent.com/XPWMBvcM_RabfYknam87iAIocJvKHfZ9Uu7KGGBT3XcejHX1na2j0oM6bwUuGl6cfdlkHKkrIdlI)![enter image description here](https://lh3.googleusercontent.com/4BmZ_dHcBSPJhsvBuz68lPdb4gSwHYNCSCQVyjDyjoyLk5kr26s-BFpWVu1udz-MPpaB2vsq47IN)![enter image description here](https://lh3.googleusercontent.com/PVRTLHKzQwfTkwT-OPUIRDu7alohHm2DWcfKif0UwzvtcgfoXZ_cWuG93s-LUhJPVuUr_f1q797V)

## 3) 단어장, 문법분석 
원문 가사를 Google ML Natural Language API를 통해서 문법을 분석하고 중요한 동사, 명사를 추출하여 단어장을 만들어 제공하고 원문 가사의 각 단어에 문법 태그를 맵핑한 정보를 제공합니다.
(위 기능은 다소 시간이 걸리므로 처음에는 내용이 안 보일 수 있지만 기다리면 추가됩니다.)


![enter image description here](https://lh3.googleusercontent.com/NaBlvTZO3GbU4JKiyxr7BvZJX_hr4vO7NX0fzYgPXV0CrqmhxG1PuTjBnChJKF1ZC0A5-8dsmGOc)![enter image description here](https://lh3.googleusercontent.com/_SS4mSNxNwZzXLoCpThNvNYV4oVghr7vknC1UEtQ3lTxVDGGus_QrT7GZuJUkXL5lHg2LHCmgozN)

## 4. 나만의 단어장
1. 단어장에서 자신이 원하는 단어를 클릭할 경우 나만의 단어장에 추가되어 자주 보고 외우고 싶은 단어를 따로 볼 수 있습니다.
(나만의 단어장은 모든 음악정보 액티비티에서 동일한 내용을 보여줍니다.)
2. 나만의 단어장에서 하나의 단어를 꾹 누르면 삭제 확인 메세지가 생성되며 "확인"버튼을 누를 경우 해당 단어가 나만의 단어장에서 삭제됩니다.

![enter image description here](https://lh3.googleusercontent.com/jvJom9sWHmb2PfRds_97Uh5_FLI-UvQROoMmIfhiv-BbUNKkYwuhSUju3DYJKH1-A8l_h0PfQX5q)![enter image description here](https://lh3.googleusercontent.com/Ev50hyTFLmLRru3r5rBRbl5HgAwsczQF4HP-CODwKcEJdeWVr_6HsCFfGXZ5IJoQMhW-xYACMhWn)![enter image description here](https://lh3.googleusercontent.com/q6aeaHyTj_ZilG1fdKwWammA0mf-wjnQcvGvDJprtyox1k-E0yujLYrJD4W_Ga3gDQqxOo3c9jft)

## 오늘의 단어
1. 나만의 단어장에 10개 이상의 단어가 존재하는 경우 메인화면에 있는 "오늘의 단어를 맞춰주세요" 버튼을 클릭하여 단어 테스트 액티비티로 이동 할 수 있습니다. ( 10개 이하 일 경우 클릭 시 10개 이상의 단어를 추가하라는 매세지가 생성됩니다.)
2. 단어 테스트 액티비티에서는 10개의 단어가 무작위로 선별되고 한 단어 당 4가지 뜻을 골라 맞출 수 있는 기능을 제공합니다.
3. 
![enter image description here](https://lh3.googleusercontent.com/xn4vWMHbw1Pf9-owfvtmHNLh3iy1esz05l5H2DjRd-pPsOJai1PzzLKNRRzqhx8gqd42ElWMd-tf)![enter image description here](https://lh3.googleusercontent.com/G2JkmBDFYIvBG613f56ue0LSWeKhBY8U4MU0Uy9UHnAmVjvldwdZV_Yuj3CdY4AZBp1uBp4aW3A2)

## 다국어 지원
팝송으로 영어를 공부하고 싶은건 비단 한국인 뿐만이 아닙니다. LEBPS는 중국어, 일본어까지 지원하는 번역 및 지원하는 기능을 가지고 있습니다.
(사용자의 환경설정에서 사용언어를 가져오기 때문에 따로 언어를 설정 할 필요가 없습니다.)

> 일본어
> 
![enter image description here](https://lh3.googleusercontent.com/T3-x1_QuKt1dtSm5ARwt4KiSElEEd_ukZ2lMmfQMVkHjkt5lPIWLJ0qiALZ9o0BWLv-dTXkcZA6Q)![enter image description here](https://lh3.googleusercontent.com/apZ-Wa_NxM1Pd9nFvi106HG3YBKaG1abGdn5cKnha56VB9jHiKZgCblOYfdUXWD9o7vhjL6a-Z0D)![enter image description here](https://lh3.googleusercontent.com/qmt3e7JHXv4n-i_JpCFGFUeL6gJ1xxNMXXfSskFXK5RSK6g0iIa2WnQ29WQsY4LSmWdzJ7paL-HY)![enter image description here](https://lh3.googleusercontent.com/WRsy43aQCxYP68Re645K7JLnEpObeyVViv4_nIT2rWuXc-Lqf5aFkxwE84FhcuXJpf-V180ka7Yd)

> 중국어
> 
![enter image description here](https://lh3.googleusercontent.com/28rf0PF8QEniGD0gSuuX4RIGfPTT40G0ZGWRPGaAJiKPGWMf6j6QuCwCEFPQssuS68mYhNnAK_-7)![enter image description here](https://lh3.googleusercontent.com/FY--GCCa6YY7sMF-1ec2SNq3Oj_h5C5E0XB-8MfKFGMjciKFukbgYaSVDNdCdMZ8_5frjsTTT6f2)![enter image description here](https://lh3.googleusercontent.com/ij1XC9WmABbjLGgJ_EqpnhpOZLlKeDLP75p7T85z3zY4yEEnWJslBIfo11i_DtnsTZ-EgdZnP8V4)![enter image description here](https://lh3.googleusercontent.com/IRvqP6Uy3iHAp6akoRW9g0IiN-ZiLAgcLtizSL51Ji40qxYXxRAUe45sJfWr7kcLKVdYYlDcR3hZ)
## FlowChart

![enter image description here](https://lh3.googleusercontent.com/F1QjEKTZar3FJl8rujvFkhBjAXUwN140pF-AuWWRFbaPubHLFkrvM0Jf7_jkykM7YRDhtSj8ZMQL)

링크: https://drive.google.com/file/d/196raueUf-o3dMd3a7oC_GkmsEoiybNvB/view?usp=sharing
## 파일 정보 및 목록 (File Manifest)

 - com.example.lebps(package)
	 - DictionaryAPI
		 - GetMean.class : 생성자로 번역할 문자열을 제공받고 Google Translation API에 접속해서 문자열을 번역하기 위한 클래스
	 - Lyrics_Activity
		 - MyWordListViewAdapter.class : 나만의 단어장에 있는 ListView에 데이터를 업로드하기 위한 클래스
		 - NaturalML,class : 생성자로 분석할 문자열을 제공받고 Google ML Natural Language API에 접속하여 문자열을 분석, 데이터를 받아서 처리하기 위한 클래스
		 - TrackInfoActivity.class :  원문 가사, 번역을 직접 처리하고 단어장, 문법 분석 등은 다른 클래스에서 데이터를 받아 처리하는 액티비티 클래스
		 - WordListViewAdapter.class : GetMean.class 와 NaturalML.class를 활용하여 추출된 중요한 단어들을 ListView에 업로드하기 위한 클래스
	 - TestWord
		 - WordTestActivity.class : 나만의 단어장에서 10개의 단어를 추출하여 단어테스트 기능을 제공하기 위한 액티비티 클래스
		 - WordTestAdapter.class : 추출된 단어들을 ListView에 업로드 하기 위한 클래스
	 - MainActivity : Musixmatch API를 사용하여 음악을 검색하고 메인화면을 처리하는 액티비티 클래스
	 - ListViewAdapter : 메인화면의 음악리스트에 정보를 업로드하기 위한 클래스
## 배포자 및 개발자의 연락처 정보 (Contact Information)

Name : Jae Seong Lee
phone :  +821025066791	
email :  lee01042000@gmail.com
