import request from '@/utils/request'

export function listAlbum() {
    return request({
        url: '/album',
        method: 'get'
    })
}

export function listAlbumByTitle(query) {
    return request({
        url: '/album/title',
        method: 'get',
        params: query
    })
}

export function listAlbumByVenue(query) {
    return request({
        url: '/album/type',
        method: 'get',
        params: query
    })
}

export function listAlbumType() {
    return request({
        url: '/album/type',
        method: 'get'
    })
}

export function createAlbum(obj) {
    return request({
        url: '/album',
        method: 'post',
        data: {
            ...obj
        }
    })
}

export function updateAlbum(obj) {
    return request({
        url: '/album/' + obj.id,
        method: 'put',
        data: {
            ...obj
        }
    })
}

export function removeAlbum(id) {
    return request({
        url: '/album/' + id,
        method: 'delete'
    })
}