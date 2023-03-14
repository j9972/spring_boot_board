## EndPoints

### 홈페이지로 이동

Get /

ex) http://localhost:2020/

### 새로운 게시글 작성 페이지로 이동

Get board/write

ex) http://localhost:2020/board/write

### 글을 작성하는 페이지

Post /board/writepro

ex) http://localhost:2020/board/writepro

### 게시글 목 페이지로 이동

Get /board/list

ex) http://localhost:2020//board/list

### 게시글 세부 목록 보기

Get /board/view

ex) http://localhost:2020/board/view

### 게시글 세부 목록 삭제하기

Get /board/delete

ex) http://localhost:2020/board/delete

### 게시글 세부 목록 수정하기

Get /board/modify/{id}

ex) http://localhost:2020/board/modify?id=1

### 게시글 세부 목록 업데이트 하기

Get /board/update/{id}

ex) http://localhost:2020/board/update?id=1

## 추가 작업
### 글작성 시에 Message 창 띄워주는 작업해 줍니다.
script 문법을 사용해서 글 작업 완료시 보여줍니다

### 파일 첨부
form 태그 안에 enctype="multipart/form-data" 필요