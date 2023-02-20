<template>
  <div>
    <Navbar />
    <Header :title="'Album'" :button="'Add Album'" @add="setShowAddAlbumModal(true)" />
    <div class="album-container">
      <div class="search-container">
        <select v-model="selectedAlbumType" @change="changeSearchField()">
          <option label="Select Album Type" value="0" disabled />
          <option v-for="item in allAlbumTypes" :key="item.value" :label="item.label" :value="item.value" />
        </select>
        <input type="text" :placeholder="searchField" v-model="searchText">
        <button @click="search">Search</button>
        <button @click="reset" style="margin-left:10px;">Reset</button>
      </div>
      <table style="width: 100%;flex: 1;">
        <thead>
          <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Genre</th>
            <th>Cover Image</th>
            <th>Type</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(album, index) in albums" :key="index">
            <td>{{ album.title }}</td>
            <td>{{ album.description }}</td>
            <td>{{ album.genre }}</td>
            <td>{{ album.coverImage }}</td>
            <td>{{ album.typeName }}</td>
            <td style="width:260px">
              <button @click="handleViewSongs(album)">View Song</button>
              <button @click="handleEditAlbum(album)">Edit</button>
              <button @click="handleDeleteAlbum(album)">Delete</button>
            </td>
          </tr>
        </tbody>
      </table>

      <!-- Add Album Modal -->
      <modal v-if="showAddAlbumModal" :is-active="showAddAlbumModal">
        <h2>Add Album</h2>
        <el-form ref="newAlbum" :model="newAlbum" :rules="albumRules" class="form-container">
          <el-form-item label="Title: " prop="title">
            <el-input v-model="newAlbum.title" />
          </el-form-item>
          <el-form-item label="Description: " prop="description">
                <el-input v-model="newAlbum.description" />
            </el-form-item>
             <el-form-item label="Genre: " prop="genre">
                  <el-input v-model="newAlbum.genre" />
              </el-form-item>
              <el-form-item label="Release Date: " prop="releaseDate" >
                  <el-date-picker
                  :append-to-body="false"
                  v-model="newAlbum.releaseDate"
                  type="date"
                  placeholder="Select a date"
                ></el-date-picker>
              </el-form-item>
            <el-form-item label="Cover Image: " prop="coverImage">
                <el-input v-model="newAlbum.coverImage"></el-input>
            </el-form-item>
            <el-form-item label="Album Type: " prop="type">
              <el-radio-group v-model="newAlbum.type">
                  <el-radio-button v-for="item in allAlbumTypes" :key="item.value" :label="item.value">{{
                    item.label }}</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="Artist: " prop="artistId">
                <el-select v-model="newAlbum.artistId" placeholder="Select Artist" :popper-append-to-body="false">
                    <el-option v-for="item in artistList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
            </el-form-item>
            <el-form-item v-show="newAlbum.type === 2" label="Platform: " prop="platform"
            :rules="newAlbum.type === 2 ? albumRules.platform : [{ required: false }]">
              <el-input v-model="newAlbum.platform"></el-input>
            </el-form-item>
            <el-form-item v-show="newAlbum.type === 3" label="Venue: " prop="venue"
            :rules="newAlbum.type === 3 ? albumRules.venue : [{ required: false }]">
              <el-input v-model="newAlbum.venue"></el-input>
            </el-form-item>
            <el-form-item v-show="newAlbum.type === 3" label="Year: " prop="year"
            :rules="newAlbum.type === 3 ? albumRules.year : [{ required: false }]">
              <el-date-picker
                :append-to-body="false"
                v-model="newAlbum.year"
                type="year"
                format="yyyy"
                value-format="yyyy"
                placeholder="Select a year"
              ></el-date-picker>
            </el-form-item>

        </el-form>
        <div class="modal-footer">
            <el-button @click="handleAdd">Add</el-button>
          <el-button @click="showAddAlbumModal = false">Cancel</el-button>
        </div>
      </modal>

      <!-- Edit Album Modal -->
      <modal v-if="showEditAlbumModal" :is-active="showEditAlbumModal">
        <h2>Edit Album</h2>
        <el-form ref="editAlbum" :model="editAlbum" :rules="albumRules" class="form-container">
          <el-form-item label="Title: " prop="title">
            <el-input v-model="editAlbum.title" />
          </el-form-item>
          <el-form-item label="Description: " prop="description">
            <el-input v-model="editAlbum.description" />
          </el-form-item>
          <el-form-item label="Genre: " prop="genre">
            <el-input v-model="editAlbum.genre" />
          </el-form-item>
          <el-form-item label="Release Date: " prop="releaseDate" >
                  <el-date-picker
                    :append-to-body="false"
                    v-model="editAlbum.releaseDate"
                    type="date"
                    placeholder="Select a date"
                  ></el-date-picker>
              </el-form-item>
          <el-form-item label="Cover image: " prop="coverImage">
           <el-image v-model="editAlbum.coverImage" src="newAlbum.coverImage" alt=''/>
          </el-form-item>
          <el-form-item label="Album Type: " prop="type">
            <el-radio-group v-model="editAlbum.type">
              <el-radio-button v-for="item in allAlbumTypes" :key="item.value" :label="item.value">{{
                item.label }}</el-radio-button>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="Artist: " prop="artistName">
            <el-input v-model="editAlbum.artistName" disabled></el-input>
          </el-form-item>
          <el-form-item v-show="editAlbum.type === 2" label="Platform: " prop="platform"
              :rules="editAlbum.type === 2 ? albumRules.platform : [{ required: false }]">
                <el-input v-model="editAlbum.platform"></el-input>
              </el-form-item>
              <el-form-item v-show="editAlbum.type === 3" label="Venue: " prop="venue"
              :rules="editAlbum.type === 3 ? albumRules.venue : [{ required: false }]">
                <el-input v-model="editAlbum.venue"></el-input>
              </el-form-item>
              <el-form-item v-show="editAlbum.type === 3" label="Year: " prop="year"
              :rules="editAlbum.type === 3 ? albumRules.year : [{ required: false }]">
                <el-date-picker
                  :append-to-body="false"
                  v-model="editAlbum.year"
                  type="year"
                  format="yyyy"
                  value-format="yyyy"
                  placeholder="Select a year"
                ></el-date-picker>
              </el-form-item>
        </el-form>
        <div class='modal-footer'>
          <el-button @click="handleEditComfirm">Confirm</el-button>
          <el-button @click="showEditAlbumModal = false">Cancel</el-button>
        </div>

      </modal>

      <!-- Music List Modal -->
      <modal v-if="showMusicListModal" :is-active="showMusicListModal" >
        <h2>All Songs</h2>
        <el-table :data="musicList" border>
          <el-table-column property="title" label="title" />
          <el-table-column property="genre" label="genre" />
        </el-table>
        <div class='modal-footer'>
          <el-button @click="showMusicListModal = false">Close</el-button>
        </div>

      </modal>
    </div>
  </div>
</template>

<script>
import Modal from '@/components/Modal.vue';
import Header from '@/components/Header.vue';
import Navbar from '@/components/Navbar.vue';
import { listAlbum, listAlbumType, listAlbumByTitle, listAlbumByVenue, createAlbum, updateAlbum, removeAlbum } from '@/api/album';
import { getArtist, listArtist } from '@/api/artist';
import { getMusicList } from '@/api/music';

export default {
  components: {
    Navbar,
    Header,
    Modal
  },
  name: 'Home',
  data() {
    return {
      searchText: '',
      searchField: 'Title',
      selectedAlbumType: 0,
      albums: [],
      albumMap: {},
      musicList: [],
      artistList: [],
      editAlbum: {
        id: null,
        title: '',
        description: '',
        genre: '',
        coverImage: '',
        artistId: null,
        artistName: '',
        type: 1,
        releaseDate: '',
        venue: '',
        year: null,
        platform: ''
      },
      newAlbum: {
        title: '',
        description: '',
        genre: '',
        coverImage: '',
        artistId: null,
        artistName: '',
        type: 1,
        releaseDate: '',
        venue: '',
        year: null,
        platform: ''
      },
      showAddAlbumModal: false,
      showEditAlbumModal: false,
      showMusicListModal: false,
      allAlbumTypes: [],
      albumRules: {
        title: [{ required: true, message: "Please enter a title", trigger: 'blur' }],
        releaseDate: [{ required: true, message: 'Please select a date', trigger: 'blur' }],
        type: [{ required: true, message: 'Please select a type', trigger: 'blur' }],
        artistId: [{ required: true, message: 'Please select an artist', trigger: 'blur' }],
        platform: [{ required: true, message: 'Please enter a platform', trigger: 'blur' }],
        venue: [{ required: true, message: 'Please enter a venue', trigger: 'blur' }],
        year: [{ required: true, message: 'Please select a year', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      // get all album types
      this.getAllAlbumTypes();
      // get album list
      this.getAlbumList();
    },
    changeSearchField() {
      if (this.albumMap[this.selectedAlbumType] === 'LIVE') {
        this.searchField = 'Venue'
      } else {
        this.searchField = 'Title'
      }
    },
    search() {
      if (this.selectedAlbumType == 0 || this.searchText === '') {
        this.$message({
          type: 'warning',
          message: 'Please select album type'
        });
        return;
      } else if (this.albumMap[this.selectedAlbumType] === 'LIVE') {
        listAlbumByVenue({ venue: this.searchText })
          .then((response) => {
            this.getFilteredAlbumList(response)
          })
      } else {
        listAlbumByTitle({ title: this.searchText })
          .then((response) => {
            this.getFilteredAlbumList(response)
          })
      }
    },
    reset() {
      this.searchField = 'Title'
      this.selectedAlbumType = 0
      this.searchText = ''
      this.getAlbumList()
    },
    setShowAddAlbumModal(flag) {
      this.showAddAlbumModal = flag
      if (flag) {
        this.newAlbum = {
          title: '',
          description: '',
          genre: '',
          coverImage: '',
          artistId: null,
          artistName: '',
          type: 1,
          releaseDate: '',
          venue: '',
          year: null,
          platform: ''
        }
        this.getArtistList();
      }
    },
    getArtistList() {
      listArtist()
        .then((response) => {
          this.artistList = response
        });
    },
    handleAddAlbum() {
      const temp = Object.keys(this.editAlbum)
      temp.map(key => {
        this.editAlbum[key] = album[key]
      })
      getArtist(this.editAlbum.artistId)
        .then((response) => {
          this.editAlbum.artistName = response.name;
        })
    },
    addAlbum() {
      this.showAddAlbumDialog = false;
    },
    handleDeleteAlbum(album) {
      removeAlbum(album.id)
        .then((response) => {
          this.getAlbumList();
        });
    },
    getAlbumList() {
      listAlbum()
        .then((response) => {
          this.getFilteredAlbumList(response)
        });
    },
    getFilteredAlbumList(data) {
      this.albums = [];
      for (const element of data) {
        element['typeName'] = this.albumMap[element.type]
        this.albums.push(element)
      }
    },
    // get all album types
    getAllAlbumTypes() {
      this.allAlbumTypes = []
      listAlbumType()
        .then((response) => {
          this.albumMap = response;
          var obj = response
          for (var key in obj) {
            var optionObj = { value: parseInt(key), label: obj[key] };
            this.allAlbumTypes.push(optionObj);
          }
        });
    },
    handleAdd() {
      this.$refs['newAlbum'].validate(valid => {
        if (valid) {
          createAlbum(this.newAlbum)
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
                message: 'Add album failed'
              });
            });
          this.setShowAddAlbumModal(false);
        }
      })
    },
    setShowMusicListModal(flag) {
      this.showMusicListModal = flag
    },
    handleViewSongs(album) {
      this.setShowMusicListModal(true)
      getMusicList(album.id)
        .then((response) => {
          this.musicList = response;
        })
    },
    handleEditAlbum(album) {
      this.setShowEditAlbumModal(true);
      const temp = Object.keys(this.editAlbum)
      temp.map(key => {
        this.editAlbum[key] = album[key]
      })
      getArtist(this.editAlbum.artistId)
        .then((response) => {
          this.editAlbum.artistName = response.name;
        })
    },
    setShowEditAlbumModal(flag) {
      this.showEditAlbumModal = flag
    },
    handleEditComfirm() {
      this.$refs['editAlbum'].validate(valid => {
        if (valid) {
          updateAlbum(this.editAlbum)
            .then((response) => {
              this.getAlbumList();
              this.$message({
                type: 'success',
                message: 'Success'
              });
            })
            .catch(error => {
              this.$message({
                type: 'error',
                message: 'Update album failed'
              });
            });
          this.setShowEditAlbumModal(false);
          this.resetEditForm();
        }
      })
    },
    resetEditForm() {
      this.editAlbum = {
        id: null,
        title: '',
        description: '',
        genre: '',
        coverImage: '',
        artistId: null,
        artistName: '',
        type: 1,
        releaseDate: '',
        venue: '',
        year: null,
        platform: ''
      }
    }
  }
}
</script>
<style scoped>
.album-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 20px 40px 20px 40px;
}

.search-container {
  margin: 16px 0;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.search-container select {
  padding: 6px 10px;
  font-size: 14px;
}

.search-container input {
  border: 1px solid #ccc;
  padding: 6px 10px;
  margin-right: 10px;
  font-size: 14px;
  width: 200px;
}

.search-container button {
  background-color: #343a40;
  color: #fff;
  border: none;
  height: 30px;
  border-radius: 4px;
  padding: 6px 10px;
  font-size: 14px;
  cursor: pointer;
}

.search-container button:hover {
  background-color: #f9ad28;
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
