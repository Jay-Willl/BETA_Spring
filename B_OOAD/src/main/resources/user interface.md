# user端接口文档

## 1.登录注册

- 登录

```typescript
url: "/user/login"
method: "post"
request format:
{
  username: "user111",
  password: "123456",
}
response format:
```

- 注册

```typescript
url: "/user/register"
method: "post"
request format:
{
  username: "user111",
  email: "123@123.com"
  password: "123456"
}
response format:

```

## 2.主页

1.   获取主页走马灯中的课程（从course中挑几个）

```typescript
url: "/user/banners"
method: "get"
request format:{}
response format:
{
    { course_id
    course_title
    course_cover
    course_price},
    ...
    { course_id
    course_title
    course_cover
    course_price},
}
```

2.   获取主页推荐视频（从course中挑几个）

```typescript
url: "/user/maincourses"
method: "get"
request format:{}
response format:
{
    { course_id
    course_title
    course_cover
    course_price},
    ...
    { course_id
    course_title
    course_cover
    course_price},
}
```

---



## 3.课程搜索页面

- ```url
  ("/user/course/{page}/{per_page}/{strategy}")
  ("/course/search/{page}/{per_page}/{text}/{strategy}")
  ```
  
  
  
  (1.3更新response format)获取所有课程
  
  ```typescript
  url: "/user/course/1/${limit}/all"
  method: "get"
  request format:{}
  response format:
  {
      { course_id
      course_title
      course_cover
      course_price
      course_comment_count //1.3
      courese_buy_count	//1.3
      },
      ......
  }
  ```
  
  (1.3更新，搜索url变更)获取搜索的课程
  
  ```typescript
  url: "/user/course/1/${limit}/search/{search_string}"
  method: "get"
  request format:{}
  response format:
  {
      { course_id
      course_title
      course_cover
      course_price
      course_comment_count //1.3
      courese_buy_count	//1.3
      },
      ...
  }
  ```


---



## 4.课程详情页面

1.   获取指定课程所有信息

```typescript
url: "/user/course/{course_id}"
method: "get"
request format:{}
response format:
{
    course_id
    course_title
    course_description
    course_cover
    course_price
    course_score
    teacher_id 
    chapter_list:[
    	{	
            chapter_id
            chapter_index
            chapter_title
            lesson_list:[
            	{	
            		lesson_id
            		lesson_index
            		lesson_title
            		lesson_description
            		lesson_viewcounts
            		lesson_score
        		}，
            ]
    	}
    ]
}
```

2.   (1.3评论要返回数组)获取课程的评论

```typescript
url: "/user/comment/{course_id}"
method: "get"
request format:{}
response format:{
    {
        comment_id
        user_id
        comment_content
        comment_time
    }
    ......
}
```

3.（1.3更新 request format）提交课程评论

```typescript
url: "/user/course/addComment"
method: "post"
request format:
{
    user_id 
    course_id  
    comment_content 
 }
   
response format:
```

4.   (1.3更新 提交购买记录)

```
url: "/user/buy/{course_id}/{user_id}"
method: "get"
request format:
{}  
response format:
```

---



## 5.播放页面

1.   获取视频播放的url或者凭证

```typescript
url: "/user/video/getVideoUrl/{lesson_id}"
method: "get"
request format:{}
response format:
url
```

2.   （1.3更新）获取用户的lesson的完成情况

```
url: "/user/getUerscourse/{course_id}/{user_id}"
method: "get"
request format:{}
response format:{
	{
		chapter_index: 1,
		lesson_index: 2
	},
	{
		chapter_index: 2,
		lesson_index: 2
	},
}
```

3.   （1.3更新 url）提交lesson已完成观看

```typescript
url: "/user/lessonCompleted/{lseeon_id}/{user_id}"
method: "get"
request format:
response format:
```

---



## 6.个人界面

1.   (1.3 更新)获取指定学生基本信息

```typescript
url: "/user/info/{user_id}"
method: "get"
request format:{}
response format:{
    user_id
    user_name
    user_mail
    user_gold
    user_role
    courseList:[
        {
            course_id
            course_title
            course_description
            course_teacher
            course_grade //当前用户该门课程的成绩
		}，
        ...
    ]
}
```

---




## 7.测试(quiz)界面

1.   (1.3 更新)获取该学生的未完成的quiz

```typescript
url: "/user/getAllQuiz/{user_id}"
method: "get"
request format:{
}
response format:
{
    {
        quiz_index
        quiz_content
        course_name
        course_id
    }，
    ...
}
```

2.   (1.3)获取该学生的已完成的quiz

```
url: ""
method: "get"
request format:{
}
response format:
{
    {
        quiz_index
        quiz_content
        course_name
        quiz_score
        quiz_date
    }，
    ...
}
```

3.   提交quiz 

```typescript
  url: "/user/update/quiz"
  method: "post"
  request format:{
    user_id: "xxx",
    quiz_index: "xxx",
    course_id
    quiz_score: ""
  }
  response format:
  
```

---



## 8.作业页面

1.   (1.3 更新)获取该学生的未完成的作业

```typescript
url: "/user/homework/{user_id}"
method: "get"
request format:{}
response format:
{
    {
        homework_index
        homework_content
        course_name
        course_id
    }，
    ...
}
```

2.   (1.3)获取该学生的已完成的作业

```
url: ""
method: "get"
request format:{}
response format:
{
    {
        homework_index
        homework_content
        course_name
        course_id
        homework_score
        homework_comment
    }，
    ...
}
```

3.   （1.3）提交作业

```
  url: "/user/create/homework"
  method: "post"
  request format:
	{
	  user_id: "xxx",
      homework_index: "xxx",
      course_id,
	  pdf_id
	}
  response format:
```

4.   (1.3 更新)提交作业pdf

```typescript
  url: "/user/create/pdf"
  method: "post"
  request format:
  作业文件
  response format:
```

---



## 9.通知

1.   根据用户id获取他的所有通知

```typescript
url: "/user/notice/{user_id}"
method: "get"
request format:{}
response format:{
     {
        notice_content
        course_name
        course_id
        notice_time
    }，
    ...
}
```

---



## 10.支付

1.   提交支付

```
url: "/user/pay/{pay_amount}/{user_id}"
method:"post"

```

2.   刷新

```
url: "/user/pay/refresh"
method:"post"

```

