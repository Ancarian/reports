import {Injectable} from '@angular/core';
import {ApiServiceService} from './api-service.service';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {Report, ReportLink} from '../model/model';

@Injectable({
  providedIn: 'root'
})
export class ReportServiceService {

  constructor(private apiService: ApiServiceService, private http: HttpClient) {
  }

  public getOrganisations(): Promise<string[]> {
    return this.apiService.get(`/organisations`).toPromise();
  }

  public getRepositories(organisation: string): Promise<string[]> {
    return this.apiService.get(`/organisations/${organisation}/`).toPromise();
  }

  public getReports(organisation: string, repo: string): Promise<ReportLink> {
    return this.apiService.get(`/organisations/${organisation}/repositories/${repo}/reports`).toPromise();
  }

  public getReport(organisation: string, repo: string, reportName: string): Promise<Report> {
    return this.apiService.get(`/organisations/${organisation}/repositories/${repo}/reports/${reportName}`).toPromise();
  }

  public getLastReports(): Promise<ReportLink[]> {
    return this.apiService.get(`/last-reports/`).toPromise();
  }
}
