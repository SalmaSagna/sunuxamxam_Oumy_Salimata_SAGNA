import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-concours-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './concours-list.html',
  styleUrl: './concours-list.css',
})
export class ConcoursList implements OnInit {
  concoursListe: any[] = [];

  constructor(
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.http.get<any[]>(`${environment.apiUrl}/concours`).subscribe({
      next: (data) => {
        this.concoursListe = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Erreur chargement concours', err),
    });
  }
}
