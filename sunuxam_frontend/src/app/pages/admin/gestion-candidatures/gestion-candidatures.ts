import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

@Component({
  selector: 'app-gestion-candidatures',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './gestion-candidatures.html',
  styleUrl: './gestion-candidatures.css',
})
export class GestionCandidatures implements OnInit {
  concoursListe: any[] = [];
  concoursSelectionne: number | null = null;

  candidatures: any[] = [];
  epreuves: any[] = [];

  statuts = ['EN_ATTENTE', 'DOSSIER_COMPLET', 'ADMIS', 'REFUSE', 'EN_ATTENTE_DELIBERATION'];

  candidatureOuverte: any = null;
  statutEnCours = '';
  notesEnCours: { [epreuveId: number]: number } = {};

  constructor(
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
  ) {}

  get resultatsPublies(): boolean {
    const concours = this.concoursListe.find(c => c.id === this.concoursSelectionne);
    return concours ? concours.resultatsPublies : false;
  }

  ngOnInit() {
    this.http.get<any[]>(`${environment.apiUrl}/concours`).subscribe({
      next: (data) => {
        this.concoursListe = data;
        this.cdr.detectChanges();
      },
    });
  }

  onConcoursChange() {
    if (!this.concoursSelectionne) return;

    this.http
      .get<any[]>(`${environment.apiUrl}/candidatures/concours/${this.concoursSelectionne}`)
      .subscribe({
        next: (data) => {
          this.candidatures = data;
          this.cdr.detectChanges();
        },
      });

    this.http
      .get<any[]>(`${environment.apiUrl}/concours/${this.concoursSelectionne}/epreuves`)
      .subscribe({
        next: (data) => {
          this.epreuves = data;
          this.cdr.detectChanges();
        },
      });
  }

  ouvrirCandidature(c: any) {
    this.candidatureOuverte = c;
    this.statutEnCours = c.statut;
    this.notesEnCours = {};

    this.http.get<any[]>(`${environment.apiUrl}/notes/candidature/${c.id}`).subscribe({
      next: (notes) => {
        notes.forEach((n) => {
          this.notesEnCours[n.epreuve.id] = n.valeur;
        });
        this.cdr.detectChanges();
      },
    });
  }

  fermerCandidature() {
    this.candidatureOuverte = null;
  }

  enregistrer() {
    const candidatureId = this.candidatureOuverte.id;

    for (const e of this.epreuves) {
      const valeur = this.notesEnCours[e.id];
      if (valeur !== undefined && (valeur < 0 || valeur > 20)) {
        alert(`La note pour "${e.nom}" doit être comprise entre 0 et 20`);
        return;
      }
    }

    this.http
      .put(
        `${environment.apiUrl}/candidatures/${candidatureId}/statut?statut=${this.statutEnCours}`,
        {},
      )
      .subscribe();

    this.epreuves.forEach((e) => {
      const valeur = this.notesEnCours[e.id];
      if (valeur !== undefined) {
        this.http
          .post(
            `${environment.apiUrl}/notes?candidatureId=${candidatureId}&epreuveId=${e.id}&valeur=${valeur}`,
            {},
          )
          .subscribe();
      }
    });

    this.fermerCandidature();
    this.onConcoursChange();
  }
}
