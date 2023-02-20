import request from '@/utils/request'

export function getMusicList(albumId) {
    return request({
        url: '/music/list/' + albumId,
        method: 'get'
    })
}
export function listMusic() {
    return request({
        url: '/music',
        method: 'get'
    })
}

export function createMusic(obj) {
    return request({
        url: '/music',
        method: 'post',
        data: {
            ...obj
        }
    })
}

export function removeMusic(id) {
    return request({
        url: '/music/' + id,
        method: 'delete'
    })
}