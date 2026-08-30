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

  voirResultat(id: number) {
    this.http.get(`${environment.apiUrl}/candidatures/${id}/resultat`).subscribe({
      next: (data: any) => {
        alert(`Résultat : ${data.statut}`);
      },
      error: () => {
        alert('Les résultats ne sont pas encore publiés');
      },
    });
  }
}
