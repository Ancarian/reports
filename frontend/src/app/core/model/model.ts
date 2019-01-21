export class Report {
  perProject: PerProject;
  perRelease: PerRelease[];
}

export class ReportLink {
  organisation: string;
  repositorySummaries: Link[];
}

export class Link {
  repository: string;
  filenames: string[];
}

export class Issue {
  id: number;
  title: string;
  htmlUrl: string;
  url: string;
  user: string;
  state: string;
}

export class PerProject {
  incomingIssues: Issue[];
  backlog: Issue[];
  releases: Milestone[];
}


export class PerRelease {
  hasOverduePullRequests: boolean;
  totalEffort: boolean;
  outstandingEffort: number;
  releases: Milestone[];
}


export class Milestone {
  title: string;
  openIssues: number;
  closedIssues: number;
  url: string;
  state: string;
}

export class PullRequest {
  id: number;
  overDue: boolean;
  title: string;
  issueUrl: string;
  state: string;
}

export class ErrorMessage{
  time: string;
  message: string;
}
