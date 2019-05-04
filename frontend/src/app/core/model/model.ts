export class Report {
  globalBacklog: Issue[];
  releaseBacklog: PerRelease[];
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
  labels: string[];
  estimation: number;
  pullRequestUrl: string;
  milestoneTitle: string;
  issueStatus: string;
}

export class PerRelease {
  version: string;
  createdAt: Date;
  customIssues: Issue[];
  issueCount: number;
  integrationTime: number;
  totalDevelopmentTime: number;
}

export class ErrorMessage {
  time: string;
  message: string;
}
