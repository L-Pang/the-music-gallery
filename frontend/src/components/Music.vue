<template>
    <div>
        <Navbar />
        <Header :title="'Music'" :button="'Add Music'" @add="setShowAddMusicModal(true)"/>
        <div class="music-container">
              <table style="width: 100%;flex: 1;">
                <thead>
                  <tr>
                    <th>Title</th>
                    <th>Genre</th>
                    <th>Duration</th>
                    <th>Artist</th>
                    <th>Album</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(music, index) in musicList" :key="index">
                    <td>{{ music.title }}</td>
                    <td>{{ music.genre }}</td>
                    <td>{{ music.duration }}</td>
                    <td>{{ music.artistName }}</td>
                    <td>{{ music.albumName }}</td>
                    <td style="width:260px">
                      <button @click="handleDeleteMusic(album)">Delete</button>
                    </td>
                  </tr>
                </tbody>
              </table>

              <!-- Add Music Modal -->
              <modal v-if="showAddMusicModal" :is-active="showAddMusicModal">
                <h2>Add Music</h2>
                <el-form ref="newMusic" :model="newMusic" :rules="musicRules" class="form-container">
                  <el-form-item label="Title: " prop="title">
                    <el-input v-model="newMusic.title" />
                  </el-form-item>
                  <el-form-item label="Genre: " prop="genre">
                        <el-input v-model="newMusic.genre" />
                    </el-form-item>
                    <el-form-item label="Duration: " prop="duration">
                        <el-input v-model="newMusic.duration" />
                    </el-form-item>
                    <el-form-item label="Artist: " prop="artistId">
                        <el-select v-model="newMusic.artistId" placeholder="Select Artist" :popper-append-to-body="false" @change="getAlbumListByArtistId()">
                            <el-option v-for="item in artistList" :key="item.id" :label="item.name" :value="item.id" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="Album: " prop="albumId">
                            <el-select v-model="newMusic.albumId" placeholder="Select Album" :popper-append-to-body="false">
                                <el-option v-for="item in artistAlbumList" :key="item.id" :label="item.title" :value="item.id" />
                            </el-select>
                        </el-form-item>
                </el-form>
                <div class="modal-footer">
                    <el-button @click="handleAdd">Add</el-button>
                  <el-button @click="showAddMusicModal = false">Cancel</el-button>
                </div>
                
              </modal>

            </div>
    </div>
</template>

<script>
import Modal from '@/components/Modal.vue';
import Header from '@/components/Header.vue';
import Navbar from '@/components/Navbar.vue';
import { listAlbum } from '@/api/album';
import { listArtist } from '@/api/artist';
import { listMusic, createMusic, removeMusic } from '@/api/music';

export default {
    components: {
        Navbar,
        Header,
        Modal
    },
    name: 'Music',
    data() {
        return {
            showAddMusicModal: false,
            musicList: [],
            artistList: [],
            albumList: [],
            artistAlbumList: [],
            artistMap: {},
            albumMap: {},
            newMusic: {
                titile: '',
                genre: '',
                duration: 0,
                artistId: null,
                albumId: null,
            }, 
            musicRules: {
                title: [{ required: true, message: "Please enter a title", trigger: 'blur' }],
                artistId: [{ required: true, message: "Please select an artist", trigger: 'blur' }],
                albumId: [{ required: true, message: "Please select an album", trigger: 'blur' }]
            }
        }
    },
    created() {
        this.init()
    },
    methods:{
        init() {
            this.getMusicList();
        },
        getMusicList() {
             this.musicList = []
            listMusic()
                .then((response) => {
                    var music = response
                    listArtist()
                        .then((response) => {
                            this.artistList = response
                            for (const element of this.artistList) {
                                this.artistMap[element.id] = element.name

                            }
                            listAlbum()
                                .then((response) => {
                                    this.albumList = response
                                    for (const element of this.albumList) {
                                        this.albumMap[element.id] = element.title
                                    }
                                     for (const element of music) {
                                        element.artistName = this.artistMap[element.artistId]
                                        element.albumName = this.albumMap[element.albumId]
                                        this.musicList.push(element)
                                    }

                                });
                        });
                });
        },
        getAlbumListByArtistId() {
            this.artistAlbumList = []
            for (const element of this.albumList) {
                if (element.artistId == this.newMusic.artistId) {
                    this.artistAlbumList.push({id: element.id, title: element.title})
                }
            }
        },
        setShowAddMusicModal(flag) {
            this.showAddMusicModal = flag
            if (!flag) {
                this.$refs.newMusic.resetFields()
            }
        },
        handleAdd() {
            this.$refs['newMusic'].validate(valid => {
                if (valid) {
                    createMusic(this.newMusic)
                        .then((response) => {
                            this.$message({
                                type: 'success',
                                message: 'Success'
                            });
                            this.init();
                        })
                    this.setShowAddMusicModal(false);
                }
            })
        },
        handleDeleteMusic(artist) {
            removeMusic(artist.id)
                .then((response) => {
                    this.$message({
                        type: 'success',
                        message: 'Success'
                    });
                    this.init();
                })
                .catch(error => {
                    this.$message({
                        type: 'error',
                        message: 'Delete artist failed'
                    });
                });
        }
    }
}
</script>
<style scoped>
.music-container {
    display: flex;
    flex-direction: column;
    height: 100%;
    padding: 20px 40px 20px 40px;
}

table {
  width: 100%;
  height: auto;
  border-collapse: collapse;
  border: 1px solid #ddd;
}

th,
td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

table button {
  background-color: #fff;
  color: #343a40;
  border: 1px solid #343a40;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
}
table button:hover {
  background-color: #ffa000;
}

.form-container {
  display: flex;
  flex-direction: column;
  padding: 0 40px 0 40px;
}

.modal-footer {
  margin-top: 30px;
}
</style>