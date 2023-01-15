import { createWebHistory, createRouter } from "vue-router";

import mainpage from '../pages/mainpage.vue'
import course from '../pages/course.vue'
import courseinfo from '../pages/courseinfo.vue'
import login from '../pages/login.vue'
import quiz from '../pages/quiz.vue'
import home from '../pages/home.vue'
import player from '../pages/player.vue'
import homework from '../pages/homework.vue'
import homeworksub from '../pages/homeworkSubmit.vue'
import announcement from '../pages/announcement.vue'
import payment from '../pages/payment.vue'
import quizzing from '../pages/quizzing.vue'
import userinfo from '../pages/userinfo.vue'
import back from '../pages/back.vue'

const routes = [
    {
        path: "/",

        component: mainpage,
    },
    {
        path: "/course",

        component: course,
    }, 
    {
        path: "/course/:courseid",

        component: courseinfo,
        // children:[
        //     {
        //         path:':pid',
        //         component: player,
        //     },
        // ]
    }, 
    {
        path: "/course/:courseid/:pid",
        component: player,
    }, 
    {
        path: "/course/:courseid/:pid/back",
        component: back,
    },
    {
        path: "/home",
        name: 'home',
        component: home,
        redirect: '/home/userinfo',
        children:[
            {
                path:"userinfo",
                component: userinfo,
            },
            {
                path:"homework",
                component: homework,
            },
            {
                path:"homeworkSubmit",
                component: homeworksub,
            },
            {
                path: "quiz",
                component: quiz,
            },
            {
                path: "quizzing",
                component: quizzing,
            },
            {
                path: "announcement",
                component: announcement,
            },
            {
                path: "payment",
                component: payment,
            },
        ]
    }, 
    {
        path: "/login",

        component: login,
    },     
    {      
        path: '/',         
        component: mainpage   
    },
    {
        path: '/course',
        component: course,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;


