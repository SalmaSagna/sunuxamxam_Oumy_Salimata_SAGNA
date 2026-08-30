import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { AuthService } from '../../../services/auth';

@Component({
  selector: 'app-concours-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './concours-list.html',
  styleUrl: './concours-list.css',
})
export class ConcoursList implements OnInit {
  concoursListe: any[] = [];
  concoursDejaPostules: number[] = [];

  constructor(
    private http: HttpClient,
    public authService: AuthService,
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

    if (this.authService.getRole() === 'CANDIDAT') {
      const candidatId = this.authService.getUserId();
      this.http.get<any[]>(`${environment.apiUrl}/candidatures/candidat/${candidatId}`).subscribe({
        next: (data) => {
          this.concoursDejaPostules = data.map((c) => c.concours.id);
          this.cdr.detectChanges();
        },
      });
    }
  }

  dejaPostule(concoursId: number): boolean {
    return this.concoursDejaPostules.includes(concoursId);
  }

  estOuvert(concours: any): boolean {
    const aujourdHui = new Date().toISOString().split('T')[0];
    return aujourdHui <= concours.dateLimite;
  }
}
