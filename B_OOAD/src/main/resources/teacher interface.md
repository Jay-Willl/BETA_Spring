# TeacherAdmin端接口文档

update time: 2022-1-1

## 0.说明

- 以下的url都是除去baseURL的url
- 存疑：要不要适应code/status

## 1.通用

1.1 用户登录与注册

- 登录

```typescript
url: "/teacher/login"
request format:
{
  username: "teacher123",
  password: "123456",
  role: "teacher" or "admin"
}
response format:

```

- 注册

```typescript
url: "/teacher/register"
request format:
{
  username: "teacher123",
  email: "123@123.com"
  password: "123456"
}
response format:

```

## 2.教师端

2.1 POST方法

- 获取指定老师所有被审核通过的Course

  ```typescript
  url: "/teacher/course"
  method: "post"
  request format:
  {
    username: "teacher123",
    token: "xxx"
  }
  response format:
  ```

- **（更新返回类型）**获取指定老师指定Course中的所有Chapter

  ```typescript
  url: "/teacher/chapter"
  request format:
  method: "post"
  {
    username: "teacher123",
    course: "CS303",
    token: "xxx"
  }
  response format:
  [
    {
      "index": 1,
      "title": "ch1-intro",
      "score": 30
    },
    {
      "index": 2,
      "title": "ch2-xx",
      "score": 40
    }
  ]
  ```

- **（更新返回类型）**获取指定Course指定Chapter中的所有Lessson

  ```typescript
  url: "/teacher/lesson"
  method: "post"
  request format:
  {
    username: "teacher123",
    course: "CS303",
    chapter: "Chapter1",
    token: "xxx"
  }
  response format:
  [
    {
      "index": 1,
      "lesson":  [
        {
          "index": 1,
          "title": "how to be a man1",
          "id": "xbcsjdj3djdbjsa1"
        },
        {
          "index": 2,
          "title": "how to be a man2",
          "id": "xbcsjdj3djdbjsa2"
        },
        {
          "index": 3,
          "title": "how to be a man3",
          "id": "xbcsjdj3djdbjsa3"
        },
      ]
    },
    {
      "index": 2,
      "lesson":  [
        {
          "index": 1,
          "title": "how to be a man1",
          "id": "xbcsjdj3djdbjsa1"
        }
      ]
    }
  ]
  ```

- **（更新返回类型）**获取指定Course中的所有Homework信息

  ```typescript
  url: "/teacher/homework"
  method: "post"
  request format:
  {
    username: "teacher123",
    course: "CS303",
    token: "xxx"
  }
  response format:
  [
    {
      "title": "ass1",
      "date": "2001-1-1"
    },
    {
      "title": "ass2",
      "date": "2001-1-2"
    },
  ]
  ```

- **（新）**获取指定Course中的所有Quiz信息

  ```typescript
  url: "/teacher/quiz"
  method: "post"
  request format:
  {
    username: "teacher123",
    course: "CS303",
    token: "xxx"
  }
  response format:
  [
    {
      "title": "quiz1",
      "date": "2001-1-1"
    },
    {
      "title": "quiz2",
      "date": "2001-1-2"
    },
  ]
  ```

- **（更新返回类型）**获取指定Course中的所有Grade信息

  ```typescript
  url: "/teacher/grade"
  method: "post"
  request format:
  {
    username: "teacher123",
    course: "CS303",
    token: "xxx"
  }
  response format:
  [
    {
      "name": "stu1",
      "data": [
        "chapter1": 2,
        "chapter2": 30,
        "homework1": 20,
        "quiz1": 20
        //都放在一起
      ]
    },
    {
      "name": "stu2",
      "data": [
        "chapter1": 2,
        "chapter2": 30,
        "homework1": 20,
        "quiz1": 20
        //都放在一起
      ]
    }
  ]
  ```

- **（更新返回类型）**获取指定Course中所有通知信息

  ```typescript
  url: "teacher/notice"
  method: "post"
  request format:
  {
    username: "teacher123",
    course: "CS303",
    token: "xxx"
  }
  response format:
  [
    {
      "title": "期中考试通知1",
      "Date": "2022-1-1",
      "type": "e-mail" || "system",
      "content": "xxwsdsdsadasdasd"
    },
    {
      "title": "期中考试通知2",
      "Date": "2022-1-2",
      "type": "e-mail" || "system",
      "content": "xxwsdsdsadasdasd"
    }
  ]
  ```

- **（新）**上传Course封面

  ```typescript
  url: "teacher/create/cover"
  method: "post"
  request format:
  一个图片文件
  response format:
  图片在数据库的id
  ```
  
- **（更新：把update和create分离，url变成create/course）**创建新Course

  ```typescript
  url: "teacher/create/course"
  method: "post"
  request format:
  {
    username: "zxxx",
    token: "xxxxzxz",
    data: {
      title: "xxxx",
      cover: "xxxxx",
      teacher : "xxxxx",
      price : 20,
      description : "xxxxx",
      status : "xxxxx", //checking || fail || success || delete
    }
  }
  response format:
  ```

- **（更新：把update和create分离，url变成update/course）**更新指定Course

  ```typescript
  url: "teacher/update/course"
  method: "post"
  request format:
  {
    username: "zxxx",
    token: "xxxxzxz",
    data: {
      title: "xxxx",
      cover: "xxxxx",
      teacher : "xxxxx",
      price : 20,
      description : "xxxxx",
      status : "xxxxx", //checking || fail || success || delete
    }
  }
  response format:
  ```

- **（更新：把course改成title, 外面嵌套信息**）删除Course

  ```typescript
  url: "teacher/delete/course"
  method: "delete"
  request format:
  {
    username: "zxxx",
    token: "xxxxzxz",
    data: {
      title: "xxxx",
    }
  }
  response format:
  ```

- **（更新: 改成只有创建没有修改，url改成create, 去除chapter的score）**创建/修改指定Course中的新Chapter

  ```typescript
  url: "tacher/create/chapter"
  method: "post"
  request format:
  {
    "username": "zxxx",
    "token": "xxxxzxz",
    "course": "CS303",
    "data": {
      "index": 12,
      "title": "dsdsds",
    }
  }
  response format:
  ```

- **（更新：外面嵌套信息，请求内容改成只提供index（如果两个都提供更好请给我说））**删除指定Course的指定Chapter

  ```typescript
  url: "teacher/delete/chapter"
  method: "delete"
  request format:
  {
    "username": "zxxx",
    "token": "xxxxzxz",
    "course": "CS303",
    "data": {
      "index": 12,
      //"title": "dsdsds",
    }
  }
  response format:
  ```

- **（新）**上传视频

  ```typescript
  url: "teacher/create/video"
  method: "post"
  request format:
  视频文件
  response format:
  阿里云里返回的视频id
  ```

- **（更新：外面嵌套信息，去除修改，只有创建，url从update更改为create， 添加score）**创建指定Course指定Chapter的新Lesson

  ```typescript
  url: "teacher/create/lesson"
  method: "post"
  request format:
  {
    "username": "zxxx",
    "token": "xxxxzxz",
    "course": "CS303",
    "data": {
      "index": 1,
      "title": "dsdsdsd",
      "id": "32321e214ew"
      "score"： 2 //添加score
    }
  }
  response format:
  ```

- **（更新：外面嵌套信息，url从update改成create，修改请求内容，）**创建指定Course中的新Homework

  ```typescript
  url: "teacher/create/homework"
  method: "post"
  request format:
  {
    "username": "zxxx",
    "token": "xxxxzxz",
    "course": "CS303",
    "data": {
      "title": "dsdsdsd",
      "content": "32321e214ew"
    }
  }
  response format:
  ```

- **（更新：外面嵌套信息，url从update改成create，修改请求内容，quiz添加倒计时时间，quiz类型，请在表中修改）**创建指定Course中的新quiz

  ```typescript
  url: "teacher/create/quiz"
  method: "post"
  request format:
  {
    "username": "zxxx",
    "token": "xxxxzxz",
    "course": "CS303",
    "data": {
      "title": "dsdsdsd",
      "time": 12,
      "type": "select",
      "content": "JSONcscxcsdsdsd"
    }
  }
  response format:
  ```

- **（更新：外面嵌套信息）**创建指定Course中的新通知

  ```typescript
  url: "teacher/update/notice"
  request format:
  method: "post"
  {
    "username": "zxxx",
    "token": "xxxxzxz",
    "course": "CS303",
    "data": {
      course: "xxx",
    username: "teacher1",
    useEmail: "true",
    data: "休息"
    }
  }
  response format:
  ```
  
- **获得教师所有基本信息**

  ```typescript
  url: "teacher/information"
  request format:
  method: "post"
  {
    "username": "zxxx",
    "token": "xxxxzxz",
  }
  response format:
  ```



---

- **（1.1）**获得指定课程的所有学生的chapter成绩

  ```typescript
  url: "/teacher/grade/chapter"
  method: "post"
  request format:
  {
    username: "teacher123",
    course: "CS303",
    token: "xxx"
  }
  response format:
  [
    {
      "name": "stu1",
      "data": [
        {"index": 1, "score": 30},
        "chapter2": 30,
      ]
    },
    {
      "name": "stu2",
      "data": [
        "chapter1": 2,
        "chapter2": 30,
      ]
    }
  ]
  ```

- **（1.1）**获得指定课程的所有学生的homework成绩

  ```typescript
  url: "/teacher/grade/homework"
  method: "post"
  request format:
  {
    username: "teacher123",
    course: "CS303",
    token: "xxx"
  }
  response format:
  [
    {
      "name": "stu1",
      "data": [
        "1": 2,
        "2": 30,
      ]
    },
    {
      "name": "stu2",
      "data": [
        "1": 2,
        "2": 30,
      ]
    }
  ]
  ```

- **（1.1）**获得指定课程的所有学生的quiz成绩

    ```typescript
    url: "/teacher/grade/quiz"
    method: "post"
    request format:
    {
      username: "teacher123",
      course: "CS303",
      token: "xxx"
    }
    response format:
    [
      {
        "name": "stu1",
        "data": [
          "1": 2,
          "2": 30,
        ]
      },
      {
        "name": "stu2",
        "data": [
          "1": 2,
          "2": 30,
        ]
      }
    ]
    ```

- **（1.1）**获得指定课程指定作业的所有学生的提交作业

  ```typescript
  url: "/teacher/check/homework"
  method: "post"
  request format:
  {
    username: "teacher123",
    course: "CS303",
    index: 1,
    token: "xxx"
  }
  response format:
  [
    {
      "name": "stu1",
      "url": "xsxacasdfsadsa"
    },
    {
      "name": "stu2",
      "url": "xsxacasdfsadsa"
    }
  ]
  ```

- **（1.1）**批改指定作业

  ```typescript
  url: "/teacher/review/homework"
  method: "post"
  request format:
  {
    username: "teacher123",
    course: "CS303",
    index: 1,
    name: "stu1",
    token: "xxx",
    score: 20(max100),
    commit: "dsdasdsadas"
  }
  response format:
  ```

  

---



-   (1.2) 获取当前老师的所有课程的播放量







1.   课程播放量 (饼图)
     -   课程名称
     -   播放量
2.   收入时间图(折线图)
     -   time
     -   money
     -   course_title
3.   homework成绩分布







1.   获取quiz是没做的quiz还是所有的quiz
2.   





1.   视频播放凭证
2.   course两级tags
3.   搜索算法
4.   get request cookie



