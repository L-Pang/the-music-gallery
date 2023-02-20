<template>
    <nav class="navbar">
        <div class="container">
            <router-link to="/" class="navbar-brand">
                Music Gallery
            </router-link>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <router-link to="/" class="nav-link">Album</router-link>
                </li>
                <li class="nav-item">
                    <router-link to="/artist" class="nav-link">Artist</router-link>
                </li>
                <li class="nav-item">
                    <router-link to="/music" class="nav-link">Music</router-link>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script>
export default {
    name: 'Navbar',
};
</script>

<style scoped>
.navbar {
    background-color: #343a40;
}

.navbar a {
  color: #fff;
  text-decoration: none;
  font-weight: bold;
  padding: 0.5rem;
  transition: color 0.2s ease-in-out;
}

.navbar a:hover {
  color: #ffa000;
}

.navbar a.active {
  color: #757575;
  cursor: default;
}

.container {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem;
}

.navbar-brand {
    font-size: 1.5rem;
    font-weight: bold;
}

.navbar-nav {
    display: flex;
    gap: 1rem;
    list-style: none;
}

.nav-item {
    padding: 0.5rem;
    border-radius: 0.25rem;
}

.nav-link {
    color: #fff;
    text-decoration: none;
}
</style>
