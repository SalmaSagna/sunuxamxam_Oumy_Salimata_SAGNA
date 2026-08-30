import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import { AuthService } from '../../../services/auth';

@Component({
  selector: 'app-depot-candidature',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './depot-candidature.html',
  styleUrl: './depot-candidature.css',
})
export class DepotCandidature implements OnInit {
  concoursId!: number;
  cv: File | null = null;
  photo: File | null = null;
  diplome: File | null = null;
  message = '';
  erreur = '';

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.concoursId = Number(this.route.snapshot.paramMap.get('id'));
  }

  onFileChange(event: Event, champ: string) {
    const input = event.target as HTMLInputElement;
    const fichier = input.files ? input.files[0] : null;

    if (champ === 'cv') this.cv = fichier;
    if (champ === 'photo') this.photo = fichier;
    if (champ === 'diplome') this.diplome = fichier;
  }

  onSubmit() {
    const candidatId = this.authService.getUserId();

    const formData = new FormData();
    formData.append('candidatId', String(candidatId));
    formData.append('concoursId', String(this.concoursId));
    if (this.cv) formData.append('cv', this.cv);
    if (this.photo) formData.append('photo', this.photo);
    if (this.diplome) formData.append('diplome', this.diplome);

    this.http.post(`${environment.apiUrl}/candidatures`, formData).subscribe({
      next: () => {
        this.message = 'Candidature envoyée avec succès';
        setTimeout(() => this.router.navigate(['/mes-candidatures']), 1500);
      },
      error: () => {
        this.erreur = 'Erreur lors du dépôt de la candidature';
      },
    });
  }
}
