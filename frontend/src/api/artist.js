import request from '@/utils/request'

export function getArtist(id) {
    return request({
        url: '/artist/' + id,
        method: 'get'
    })
}

export function listArtist() {
    return request({
        url: '/artist',
        method: 'get'
    })
}

export function createArtist(obj) {
    return request({
        url: '/artist',
        method: 'post',
        data: {
            ...obj
        }
    })
}

export function removeArtist(id) {
    return request({
        url: '/artist/' + id,
        method: 'delete'
    })
}