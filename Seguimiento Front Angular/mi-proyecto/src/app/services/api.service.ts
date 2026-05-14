import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Post } from '../models/post.interface';
import { User } from '../models/user.interface';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private baseUrl = 'https://jsonplaceholder.typicode.com';

  constructor(private http: HttpClient) {}

  obtenerPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.baseUrl}/posts`);
  }

  obtenerPostPorId(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.baseUrl}/posts/${id}`);
  }

  obtenerUsuarios(): Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrl}/users`);
  }

  crearPost(post: Partial<Post>): Observable<Post> {
    return this.http.post<Post>(`${this.baseUrl}/posts`, post);
  }

  eliminarPost(id: number): Observable<{}> {
    return this.http.delete(`${this.baseUrl}/posts/${id}`);
  }
}