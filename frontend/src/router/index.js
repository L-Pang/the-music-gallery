import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Music from '@/components/Music'
import Artist from '@/components/Artist'

Vue.use(Router)

export default new Router({
    routes: [{
            path: '/',
            name: 'Home',
            component: Home
        },
        {
            path: '/music',
            name: 'Music',
            component: Music
        },
        {
            path: '/artist',
            name: 'Artist',
            component: Artist
        }
    ]
})