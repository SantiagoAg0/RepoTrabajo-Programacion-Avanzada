import { Component, OnInit, signal } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { ApiService } from './services/api.service';
import { User } from './models/user.interface';
import { Post } from './models/post.interface';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [NgFor, NgIf],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  usuarios = signal<User[]>([]);
  cargando = signal(true);
  error = signal('');

  // NUEVAS SIGNALS PARA POSTS
  posts = signal<Post[]>([]);
  cargandoPosts = signal(true);
  errorPosts = signal('');

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {

    // CARGAR USUARIOS
    this.apiService.obtenerUsuarios().subscribe({
      next: (data: User[]) => {
        this.usuarios.set(data);
        this.cargando.set(false);
      },
      error: (err: Error) => {
        this.error.set('Error al cargar usuarios: ' + err.message);
        this.cargando.set(false);
        console.error('Error:', err);
      }
    });

    // CARGAR POSTS
    this.apiService.obtenerPosts().subscribe({
      next: (data: Post[]) => {
        this.posts.set(data);
        this.cargandoPosts.set(false);
      },
      error: (err: Error) => {
        this.errorPosts.set('Error al cargar posts: ' + err.message);
        this.cargandoPosts.set(false);
        console.error('Error:', err);
      }
    });
  }
}
