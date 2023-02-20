<template>
    <div>
        <Navbar />
        <Header :title="'Artist'" :button="'Add Artist'" @add="setShowAddArtistModal(true)" />
        <div class="artist-container">
          <table style="width: 100%;flex: 1;">
            <thead>
              <tr>
                <th>Name</th>
                <th>Country</th>
                <th>Description</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(artist, index) in artists" :key="index">
                <td>{{ artist.name }}</td>
                <td>{{ artist.country }}</td>
                <td>{{ artist.description }}</td>
                <td style="width:260px">
                  <button @click="handleDeleteArtist(artist)">Delete</button>
                </td>
              </tr>
            </tbody>
          </table>

          <!-- Add Artist Modal -->
          <modal v-if="showAddArtistModal" :is-active="showAddArtistModal">
            <h2>Add Artist</h2>
            <el-form ref="newArtist" :model="newArtist" :rules="artistRules" class="form-container">
              <el-form-item label="Name: " prop="name">
                <el-input v-model="newArtist.name" />
              </el-form-item>
              <el-form-item label="Country: " prop="country">
                    <el-input v-model="newArtist.country" />
                </el-form-item>
                <el-form-item label="Description: " prop="description">
                    <el-input v-model="newArtist.description" />
                </el-form-item>
            </el-form>
            <div class="modal-footer">
                <el-button @click="handleAdd">Add</el-button>
              <el-button @click="showAddArtistModal = false">Cancel</el-button>
            </div>
            
          </modal>

        </div>
    </div>
</template>

<script>
import Modal from '@/components/Modal.vue';
import Header from '@/components/Header.vue';
import Navbar from '@/components/Navbar.vue';
import { listArtist, createArtist, removeArtist } from '@/api/artist';

export default {
    components: {
        Navbar,
        Header,
        Modal
    },
    name: 'Artist',
    data() {
        return {
            artists: [],
            showAddArtistModal: false,
            newArtist: {
                name: '',
                country: '',
                description: ''
            },
            artistRules: {
                name: [{ required: true, message: "Please enter a name", trigger: 'blur' }]
            }
        }
    },
    created() {
        this.init()
    },
    methods: {
        init() {
            this.getArtistList();
        },
        // get artist list
        getArtistList() {
            listArtist()
            .then((response) => {
                this.artists = response
            });
        },
        setShowAddArtistModal(flag) {
            this.showAddArtistModal = flag
            if (!flag) {
                this.$refs.newArtist.resetFields()
            }
        },
        handleAdd() {
            this.$refs['newArtist'].validate(valid => {
                if (valid) {
                    createArtist(this.newArtist)
                        .then((response) => {
                            this.$message({
                                type: 'success',
                                message: 'Success'
                            });
                            this.init();
                        })
                    this.setShowAddArtistModal(false);
                }
            })
        },
        handleDeleteArtist(artist) {
            removeArtist(artist.id)
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
.artist-container {
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