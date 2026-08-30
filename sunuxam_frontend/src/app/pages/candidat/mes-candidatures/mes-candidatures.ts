import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { AuthService } from '../../../services/auth';

@Component({
  selector: 'app-mes-candidatures',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './mes-candidatures.html',
  styleUrl: './mes-candidatures.css',
})
export class MesCandidatures implements OnInit {
  candidatures: any[] = [];

  resultatOuvert: any = null;
  notesResultat: any[] = [];
  erreurResultat = '';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private cdr: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    const candidatId = this.authService.getUserId();

    this.http.get<any[]>(`${environment.apiUrl}/candidatures/candidat/${candidatId}`).subscribe({
      next: (data) => {
        this.candidatures = data;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Erreur chargement candidatures', err),
    });
  }

  voirResultat(c: any) {
    this.erreurResultat = '';
    this.notesResultat = [];

    this.http.get(`${environment.apiUrl}/candidatures/${c.id}/resultat`).subscribe({
      next: (data: any) => {
        this.resultatOuvert = data;

        this.http.get<any[]>(`${environment.apiUrl}/notes/candidature/${c.id}`).subscribe({
          next: (notes) => {
            this.notesResultat = notes;
            this.cdr.detectChanges();
          },
        });
      },
      error: () => {
        this.erreurResultat = 'Les résultats ne sont pas encore publiés';
        this.cdr.detectChanges();
      },
    });
  }

  fermerResultat() {
    this.resultatOuvert = null;
    this.erreurResultat = '';
  }
}
